package com.example.graphqlandroid.data.remote

import android.util.Log
import com.apollographql.apollo3.ApolloClient
import com.example.GetUserQuery
import com.example.LoginMutation
import com.example.graphqlandroid.domain.dto.authentication.LoginInputRequestDTO
import com.example.graphqlandroid.domain.dto.authentication.LoginResponseDTO
import com.example.graphqlandroid.domain.mapper.toUser
import com.example.graphqlandroid.domain.models.Results
import com.example.graphqlandroid.domain.models.AppUser
import com.example.type.LoginInput
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

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

        }
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
        }
    }
}