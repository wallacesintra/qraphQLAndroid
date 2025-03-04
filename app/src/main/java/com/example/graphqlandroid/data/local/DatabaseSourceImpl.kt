package com.example.graphqlandroid.data.local

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToOneOrNull
import com.example.graphqlandroid.AppDatabase
import com.example.graphqlandroid.domain.mapper.dashboard.toCountAggregrate
import com.example.graphqlandroid.domain.mapper.toUser
import com.example.graphqlandroid.domain.models.AppUser
import com.example.graphqlandroid.domain.models.CountAggregrate
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

class DatabaseSourceImpl(
    private val database: AppDatabase,
): DatabaseSource {

    private val queries = database.appDatabaseQueries
    override suspend fun insertUser(appUser: AppUser) {
        queries.insertUser(
            id = appUser.id,
            firstName = appUser.firstName,
            lastName = appUser.lastName,
            email = appUser.email,
            phone = appUser.phone,
            gender = appUser.gender,
            trained = appUser.trained,
            organizationId = appUser.organizationId
        )
    }

    override suspend fun getLoggedInUser(): Flow<AppUser?> {
        return queries.selectLoggedInUser()
            .asFlow()
            .mapToOneOrNull(Dispatchers.IO)
            .map { it?.toUser() }
            .flowOn(Dispatchers.Main)

    }

    override suspend fun insertCount(countAggregrate: CountAggregrate) {
        queries.transaction {

            queries.deleteCountAggregrate()

            queries.insertCountAggregrate(
                studentsCount = countAggregrate.studentsCount.toLong(),
                campsCount = countAggregrate.campsCount.toLong(),
                schoolsCount = countAggregrate.schoolsCount.toLong()
            )
        }
    }

    override suspend fun getCountAggregrate(): Flow<CountAggregrate> {
        return queries.selectCountAggregrate()
            .asFlow()
            .mapToOneOrNull(Dispatchers.IO)
            .map { it?.toCountAggregrate() ?: CountAggregrate() }
            .flowOn(Dispatchers.Main)
    }
}