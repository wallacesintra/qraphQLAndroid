package com.example.graphqlandroid.data.local

import com.example.graphqlandroid.domain.models.AppUser
import com.example.graphqlandroid.domain.models.CountAggregrate
import com.example.graphqlandroid.domain.models.school.AppSchool
import com.example.graphqlandroid.domain.models.school.DetailedSchool
import kotlinx.coroutines.flow.Flow

interface DatabaseSource {
    suspend fun insertUser(appUser: AppUser)

    suspend fun getLoggedInUser(): Flow<AppUser?>

    suspend fun insertCount(countAggregrate: CountAggregrate)

    suspend fun getCountAggregrate(): Flow<CountAggregrate>

    suspend fun insertSchool(appSchool: AppSchool)

    suspend fun getSchools(): Flow<List<AppSchool>>

    suspend fun insertDetailedSchoolInfo(detailedSchool: DetailedSchool)

    suspend fun getDetailedSchoolInfo(schoolId: String): Flow<DetailedSchool?>
}