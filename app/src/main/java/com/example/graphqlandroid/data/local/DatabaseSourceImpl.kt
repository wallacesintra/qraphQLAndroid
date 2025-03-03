package com.example.graphqlandroid.data.local

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToOneNotNull
import app.cash.sqldelight.coroutines.mapToOneOrNull
import com.example.graphqlandroid.AppDatabase
import com.example.graphqlandroid.domain.mapper.toUser
import com.example.graphqlandroid.domain.models.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

class DatabaseSourceImpl(
    private val database: AppDatabase,
): DatabaseSource {

    private val queries = database.appDatabaseQueries
    override suspend fun insertUser(user: User) {
        queries.insertUser(
            id = user.id,
            firstName = user.firstName,
            lastName = user.lastName,
            email = user.email,
            phone = user.phone,
            gender = user.gender,
            trained = user.trained,
            organizationId = user.organizationId
        )
    }

    override suspend fun getLoggedInUser(): Flow<User?> {
        return queries.selectLoggedInUser()
            .asFlow()
            .mapToOneOrNull(Dispatchers.IO)
            .map { it?.toUser() }
            .flowOn(Dispatchers.Main)

    }
}