package com.example.graphqlandroid.data.remote

import android.util.Log
import com.apollographql.apollo3.ApolloClient
import com.example.CreateCampMutation
import com.example.CreateSchoolMutation
import com.example.FetchCampQuery
import com.example.FetchOrganizationsQuery
import com.example.GetCountiesQuery
import com.example.GetCountsQuery
import com.example.GetDetailedSchoolInfoQuery
import com.example.GetSchoolsQuery
import com.example.GetUserQuery
import com.example.LoginMutation
import com.example.graphqlandroid.domain.dto.authentication.LoginInputRequestDTO
import com.example.graphqlandroid.domain.dto.authentication.LoginResponseDTO
import com.example.graphqlandroid.domain.dto.camp.CreateCamp
import com.example.graphqlandroid.domain.dto.camp.CreateCampRequestDTO
import com.example.graphqlandroid.domain.dto.school.CreateSchool
import com.example.graphqlandroid.domain.dto.school.CreateSchoolDTO
import com.example.graphqlandroid.domain.mapper.dashboard.toCountAggregrate
import com.example.graphqlandroid.domain.mapper.school.toCreateSchool
import com.example.graphqlandroid.domain.mapper.school.toDetailedSchool
import com.example.graphqlandroid.domain.mapper.school.toSchool
import com.example.graphqlandroid.domain.mapper.toAppCamp
import com.example.graphqlandroid.domain.mapper.toAppCounty
import com.example.graphqlandroid.domain.mapper.toAppOrganization
import com.example.graphqlandroid.domain.mapper.toCreateCamp
import com.example.graphqlandroid.domain.mapper.toCurriculum
import com.example.graphqlandroid.domain.mapper.toDetailedCampInfo
import com.example.graphqlandroid.domain.mapper.toUser
import com.example.graphqlandroid.domain.models.AppOrganization
import com.example.graphqlandroid.domain.models.Results
import com.example.graphqlandroid.domain.models.AppUser
import com.example.graphqlandroid.domain.models.CountAggregrate
import com.example.graphqlandroid.domain.models.camps.DetailedCampInfo
import com.example.graphqlandroid.domain.models.school.AppCamp
import com.example.graphqlandroid.domain.models.school.AppCounty
import com.example.graphqlandroid.domain.models.school.AppSchool
import com.example.graphqlandroid.domain.models.school.DetailedSchool
import com.example.type.CreateCampInput
import com.example.type.CreateSchoolInput
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

                if (response.data != null){
                    emit(Results.success(data = response.data!!.login?.let { LoginResponseDTO(token = it.token, appUser = it.user.toUser()) }!!))
                }else{
                    emit(Results.error(msg = response.errors?.first()?.message ?: "Something went wrong"))
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

    override suspend fun createSchool(createSchoolDTO: CreateSchoolDTO): Flow<Results<CreateSchool>> {
        return flow {
            try {

                val response = apolloClient
                    .mutation(mutation = CreateSchoolMutation(
                        input = CreateSchoolInput(
                            countyId = createSchoolDTO.countyId,
                            name = createSchoolDTO.name,
                            organizationId = createSchoolDTO.organizationId
                        )
                    ))
                    .execute()

                if (response.data != null){
                    emit(Results.success(data = response.data!!.createSchool?.toCreateSchool()))
                }else{
                    emit(Results.error(msg = response.errors.toString()))
                }

            }catch (e: Exception){
                emit(Results.error(e.message ?: "Error creating school"))
            }

        }.flowOn(Dispatchers.IO)
    }

    override suspend fun fetchCounties(): Flow<Results<List<AppCounty?>>> {
        return flow {
            try {
                val response = apolloClient
                    .query(query = GetCountiesQuery())
                    .execute()
                    .data

                if (response?.counties != null){
                    emit(Results.success(data = response.counties.map { it?.toAppCounty() }))
                }else{
                    emit(Results.success(data = emptyList()))
                }

            }catch (e: Exception){
                emit(Results.error(msg = e.message ?: "Error fetching counties"))
            }

        }.flowOn(Dispatchers.IO)
    }

    override suspend fun fetchOrganization(): Flow<Results<List<AppOrganization?>>> {
        return flow {
            try {
                val response = apolloClient
                    .query(query = FetchOrganizationsQuery())
                    .execute()
                    .data

                if (response?.organizations != null){
                    emit(Results.success(data = response.organizations.map { it?.toAppOrganization() }))
                }else{
                    emit(Results.success(data = emptyList()))
                }

            }catch (e: Exception){
                emit(Results.error(msg = e.message ?: "Error fetching organizations"))
            }
        }
    }

    override suspend fun createCamp(createCampRequestDTO: CreateCampRequestDTO): Flow<Results<CreateCamp>> {
        return flow {
            try {
                val response = apolloClient
                    .mutation(
                        CreateCampMutation(
                            input = CreateCampInput(
                                name = createCampRequestDTO.name,
                                startDate = createCampRequestDTO.startDate,
                                endDate = createCampRequestDTO.endDate,
                                curriculum = createCampRequestDTO.curriculum.toCurriculum(),
                                organizationId = createCampRequestDTO.organizationId,
                                schoolId = createCampRequestDTO.schoolId
                            )
                        )
                    )
                    .execute()

                if (response.data != null){
                    emit(Results.success(data = response.data!!.createCamp?.toCreateCamp()))
                }else{
                    emit(Results.error(msg = response.errors?.first()?.message ?: "Error creating camp"))
                }

            }catch (e: Exception){
                emit(Results.error(msg = e.message ?: "Error creating camp"))
            }
        }
    }

    override suspend fun fetchCamps(): Flow<Results<List<AppCamp?>>> {
        return flow {
            try {
                val response = apolloClient
                    .query(query = FetchCampQuery())
                    .execute()

                if (response.data != null){
                    emit(Results.success(data = response.data?.camps?.map { it?.toAppCamp() }))
                }else{
                    emit(Results.error(msg = response.errors?.first()?.message ?: "Error fetching camps"))
                }
            }catch (e: Exception){
                emit(Results.error(msg = e.message ?: "Error fetching camps"))
            }

        }.flowOn(Dispatchers.IO)
    }

    override suspend fun fetchDetailedCampInfo(campId: String): Flow<Results<DetailedCampInfo>> {
        return flow {
            try {
                val response = apolloClient
                    .query(
                        com.example.FetchDetailedCampInfoQuery(
                            campId = campId
                        )
                    )
                    .execute()

                if (response.data != null){
                    emit(Results.success(data = response.data?.toDetailedCampInfo()))
                }else{
                    emit(Results.error(msg = response.errors?.first()?.message ?: "Error fetching camp"))
                }

            }catch (e: Exception){
                emit(Results.error(msg = e.message ?: "Error fetching camp"))
            }
        }.flowOn(Dispatchers.IO)
    }
}