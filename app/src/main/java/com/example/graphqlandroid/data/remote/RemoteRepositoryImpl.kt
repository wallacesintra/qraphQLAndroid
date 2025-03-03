package com.example.graphqlandroid.data.remote

import android.util.Log
import com.apollographql.apollo3.ApolloClient
import com.example.LoginMutation
import com.example.graphqlandroid.domain.dto.authentication.LoginInputRequestDTO
import com.example.graphqlandroid.domain.dto.authentication.LoginResponseDTO
import com.example.graphqlandroid.domain.mapper.toUser
import com.example.graphqlandroid.domain.models.Results
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
                    emit(Results.success(data = response.login?.let { LoginResponseDTO(token = it.token, user = it.user.toUser()) }!!))
                }else{
                    emit(Results.error("Login failed"))
                }

            }catch (e: Exception){
                Log.e("LoginError", "Failed to execute GraphQL http network request", e)
                emit(Results.error(e.message ?: "Something went wrong"))

            }

        }
    }
}