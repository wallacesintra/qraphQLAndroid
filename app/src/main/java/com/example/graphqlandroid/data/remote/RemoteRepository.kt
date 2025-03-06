package com.example.graphqlandroid.data.remote

import com.example.graphqlandroid.domain.dto.authentication.LoginInputRequestDTO
import com.example.graphqlandroid.domain.dto.authentication.LoginResponseDTO
import com.example.graphqlandroid.domain.dto.camp.CreateCamp
import com.example.graphqlandroid.domain.dto.camp.CreateCampRequestDTO
import com.example.graphqlandroid.domain.dto.school.CreateSchool
import com.example.graphqlandroid.domain.dto.school.CreateSchoolDTO
import com.example.graphqlandroid.domain.models.AppOrganization
import com.example.graphqlandroid.domain.models.Results
import com.example.graphqlandroid.domain.models.AppUser
import com.example.graphqlandroid.domain.models.CountAggregrate
import com.example.graphqlandroid.domain.models.camps.DetailedCampInfo
import com.example.graphqlandroid.domain.models.school.AppCamp
import com.example.graphqlandroid.domain.models.school.AppCounty
import com.example.graphqlandroid.domain.models.school.AppSchool
import com.example.graphqlandroid.domain.models.school.DetailedSchool
import com.example.graphqlandroid.domain.models.students.AppStudent
import kotlinx.coroutines.flow.Flow

sealed interface RemoteRepository {
    suspend fun login(loginInput: LoginInputRequestDTO): Flow<Results<LoginResponseDTO>>

    suspend fun fetchUser(userId: String): Flow<Results<AppUser>>

    suspend fun fetchCountAggregrate(): Flow<Results<CountAggregrate>>

    suspend fun fetchSchools(): Flow<Results<List<AppSchool?>>>

    suspend fun fetchDetailedSchoolInfo(schoolId: String): Flow<Results<DetailedSchool>>

    suspend fun createSchool(createSchoolDTO: CreateSchoolDTO): Flow<Results<CreateSchool>>

    suspend fun fetchCounties(): Flow<Results<List<AppCounty?>>>

    suspend fun fetchOrganization():Flow<Results<List<AppOrganization?>>>

    suspend fun createCamp(createCampRequestDTO: CreateCampRequestDTO): Flow<Results<CreateCamp>>

    suspend fun fetchCamps(): Flow<Results<List<AppCamp?>>>

    suspend fun fetchDetailedCampInfo(campId: String): Flow<Results<DetailedCampInfo>>

    suspend fun fetchStudents(): Flow<Results<List<AppStudent?>>>
}