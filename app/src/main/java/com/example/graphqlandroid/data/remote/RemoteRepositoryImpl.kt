package com.example.graphqlandroid.data.remote

import android.util.Log
import com.apollographql.apollo3.ApolloClient
import com.example.GetCountsQuery
import com.example.GetDetailedSchoolInfoQuery
import com.example.GetSchoolsQuery
import com.example.GetUserQuery
import com.example.LoginMutation
import com.example.graphqlandroid.domain.dto.authentication.LoginInputRequestDTO
import com.example.graphqlandroid.domain.dto.authentication.LoginResponseDTO
import com.example.graphqlandroid.domain.mapper.dashboard.toCountAggregrate
import com.example.graphqlandroid.domain.mapper.school.toDetailedSchool
import com.example.graphqlandroid.domain.mapper.school.toSchool
import com.example.graphqlandroid.domain.mapper.toUser
import com.example.graphqlandroid.domain.models.Results
import com.example.graphqlandroid.domain.models.AppUser
import com.example.graphqlandroid.domain.models.CountAggregrate
import com.example.graphqlandroid.domain.models.school.AppSchool
import com.example.graphqlandroid.domain.models.school.DetailedSchool
import com.example.type.LoginInput
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class RemoteRepositoryImpl(
    private val apolloClient: ApolloClient
) : RemoteRepository{

    override suspend fun login(loginInput: LoginInputRequestDTO): Flow<Results<LoginResponseDTO>> {
        return flow {
            try{
                val response = apolloClient
                    .mutation(
                        LoginMutation(
                            input = LoginInput(
                                email = loginInput.email,
                                password = loginInput.password
                            )
                        )
                    )
                    .execute()
                    .data

                if (response != null){
                    emit(Results.success(data = response.login?.let { LoginResponseDTO(token = it.token, appUser = it.user.toUser()) }!!))
                }else{
                    emit(Results.error("Login failed"))
                }

            }catch (e: Exception){
                Log.e("LoginError", "Failed to execute GraphQL http network request", e)
                emit(Results.error(e.message ?: "Something went wrong"))

            }

        }.flowOn(Dispatchers.Main)
    }

    override suspend fun fetchUser(userId: String): Flow<Results<AppUser>> {
        return flow {
            try {
                val response = apolloClient
                    .query(query = GetUserQuery(userId = userId))
                    .execute()
                    .data

                if (response != null){
                    emit(Results.success(data = response.user!!.toUser() ))
                }else{
                    emit(Results.success(data = null ))
                }

            }catch (e:Exception){
                emit(Results.error(msg = e.message ?: "Something went wrong"))
            }
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun fetchCountAggregrate(): Flow<Results<CountAggregrate>> {
        return flow {
            try {
                val response = apolloClient
                    .query(query = GetCountsQuery())
                    .execute()
                    .data

                if (response != null){
                    emit(Results.success(data = response.toCountAggregrate()))
                }else {
                    emit(Results.success(data = null))
                }
            }catch (e:Exception){
                emit(Results.error(msg = e.message ?: "Something went wrong"))
            }
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun fetchSchools(): Flow<Results<List<AppSchool?>>> {
        return flow {
            try {
                val response = apolloClient
                    .query(query = GetSchoolsQuery())
                    .execute()
                    .data

                if (response?.schools != null){
                    emit(Results.success(data = response.schools.map { it?.toSchool() }))
                }else {
                    emit(Results.success(data = emptyList()))
                }

            }catch (e: Exception){
                emit(Results.error(msg = e.message ?: "Error fetching schools"))
            }
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun fetchDetailedSchoolInfo(schoolId: String): Flow<Results<DetailedSchool>> {
        return flow {
            try {
                val response = apolloClient
                    .query(query = GetDetailedSchoolInfoQuery(schoolId= schoolId))
                    .execute()
                    .data

                if (response?.school != null){
                    emit(Results.success(data = response.school.toDetailedSchool()))
                }else{
                    emit(Results.success(data = null))
                }

            }catch (e: Exception){
                emit(Results.error())
            }
        }.flowOn(Dispatchers.IO)
    }
}