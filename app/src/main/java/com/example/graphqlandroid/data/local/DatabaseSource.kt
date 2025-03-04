package com.example.graphqlandroid.data.local

import com.example.graphqlandroid.domain.models.AppUser
import com.example.graphqlandroid.domain.models.CountAggregrate
import kotlinx.coroutines.flow.Flow

interface DatabaseSource {
    suspend fun insertUser(appUser: AppUser)

    suspend fun getLoggedInUser(): Flow<AppUser?>

    suspend fun insertCount(countAggregrate: CountAggregrate)

    suspend fun getCountAggregrate(): Flow<CountAggregrate>

}