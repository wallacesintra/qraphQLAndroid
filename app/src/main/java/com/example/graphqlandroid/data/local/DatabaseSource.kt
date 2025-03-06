package com.example.graphqlandroid.data.local

import com.example.graphqlandroid.domain.models.AppOrganization
import com.example.graphqlandroid.domain.models.AppUser
import com.example.graphqlandroid.domain.models.CountAggregrate
import com.example.graphqlandroid.domain.models.camps.DetailedCampInfo
import com.example.graphqlandroid.domain.models.school.AppCamp
import com.example.graphqlandroid.domain.models.school.AppCounty
import com.example.graphqlandroid.domain.models.school.AppSchool
import com.example.graphqlandroid.domain.models.school.DetailedSchool
import com.example.graphqlandroid.domain.models.students.AppStudent
import kotlinx.coroutines.flow.Flow

interface DatabaseSource {
    suspend fun insertUser(appUser: AppUser)

    suspend fun getLoggedInUser(): Flow<AppUser?>

    suspend fun insertCount(countAggregrate: CountAggregrate)

    suspend fun getCountAggregrate(): Flow<CountAggregrate>

    suspend fun insertSchool(appSchool: AppSchool)

    suspend fun getSchools(): Flow<List<AppSchool>>

    suspend fun deleteSchools()

    suspend fun insertDetailedSchoolInfo(detailedSchool: DetailedSchool)

    suspend fun getDetailedSchoolInfo(schoolId: String): Flow<DetailedSchool?>

    suspend fun insertCounties(counties: List<AppCounty>)

    suspend fun insertCounty(appCounty: AppCounty)

    suspend fun getCounties(): Flow<List<AppCounty>>

    suspend fun insertOrganization(appOrganization: AppOrganization)

    suspend fun getOrganizations(): Flow<List<AppOrganization>>

    suspend fun insertCamp(appCamp: AppCamp)

    suspend fun getCamps(): Flow<List<AppCamp>>

    suspend fun getDetailedCampById(campId: String): Flow<DetailedCampInfo?>

    suspend fun insertDetailedCampInfo(detailedCamp: DetailedCampInfo)

    suspend fun insertStudent(appStudent: AppStudent)

    suspend fun insertStudents(appStudents: List<AppStudent>)

    suspend fun getStudents(): Flow<List<AppStudent>>
}