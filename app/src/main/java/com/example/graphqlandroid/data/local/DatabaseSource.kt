package com.example.graphqlandroid.data.local

import com.example.graphqlandroid.domain.models.User
import kotlinx.coroutines.flow.Flow

interface DatabaseSource {
    suspend fun insertUser(user: User)

    suspend fun getLoggedInUser(): Flow<User?>


}