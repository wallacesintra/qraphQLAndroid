package com.example.graphqlandroid.data.remote

import com.example.graphqlandroid.domain.dto.authentication.LoginInputRequestDTO
import com.example.graphqlandroid.domain.dto.authentication.LoginResponseDTO
import com.example.graphqlandroid.domain.models.Results
import kotlinx.coroutines.flow.Flow

sealed interface RemoteRepository {
    suspend fun login(loginInput: LoginInputRequestDTO): Flow<Results<LoginResponseDTO>>
}