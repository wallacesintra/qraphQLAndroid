package com.example.graphqlandroid.data.local

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import app.cash.sqldelight.coroutines.mapToOneOrNull
import com.example.graphqlandroid.AppDatabase
import com.example.graphqlandroid.domain.mapper.dashboard.toCountAggregrate
import com.example.graphqlandroid.domain.mapper.school.toSchool
import com.example.graphqlandroid.domain.mapper.students.toAppStudents
import com.example.graphqlandroid.domain.mapper.toAppCamp
import com.example.graphqlandroid.domain.mapper.toAppCounty
import com.example.graphqlandroid.domain.mapper.toAppOrganization
import com.example.graphqlandroid.domain.mapper.toDetailedCampInfo
import com.example.graphqlandroid.domain.mapper.toUser
import com.example.graphqlandroid.domain.models.AppOrganization
import com.example.graphqlandroid.domain.models.AppUser
import com.example.graphqlandroid.domain.models.CountAggregrate
import com.example.graphqlandroid.domain.models.camps.DetailedCampInfo
import com.example.graphqlandroid.domain.models.school.AppCamp
import com.example.graphqlandroid.domain.models.school.AppCounty
import com.example.graphqlandroid.domain.models.school.AppSchool
import com.example.graphqlandroid.domain.models.school.DetailedSchool
import com.example.graphqlandroid.domain.models.students.AppStudent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapNotNull

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

    override suspend fun insertSchool(appSchool: AppSchool) {

        queries.insertSchool(
            id = appSchool.id,
            name = appSchool.name,
            countyName = appSchool.countyName,
            countryName = appSchool.countryName
        )

    }

    override suspend fun getSchools(): Flow<List<AppSchool>> {
        return queries.selectAllSchool()
            .asFlow()
            .mapToList(Dispatchers.IO)
            .map { it.map { schoolEntity -> schoolEntity.toSchool() } }
            .flowOn(Dispatchers.Main)
    }

    override suspend fun deleteSchools() {
        queries.deleteSchools()
    }

    override suspend fun insertDetailedSchoolInfo(detailedSchool: DetailedSchool) {
        queries.transaction {
            queries.insertDetailedSchool(
                id = detailedSchool.id,
                name = detailedSchool.name,
                countyId = detailedSchool.countyId,
                organizationId = detailedSchool.organizationId,
            )

            detailedSchool.county?.let {
                queries.insertCounty(
                    id = detailedSchool.county.id,
                    name = detailedSchool.county.name,
                    latitude = detailedSchool.county.latitude,
                    longitude = detailedSchool.county.longitude,
                    countryId = detailedSchool.county.country?.id ?: "",
                    country = detailedSchool.county.country?.name ?: ""
                )

            }

            detailedSchool.county?.country?.let { country ->
                queries.insertCountry(
                    id = country.id,
                    name = country.name
                )
            }

            detailedSchool.camps.forEach { camp ->
                queries.transaction {
                    queries.insertCamp(
                        id = camp.id,
                        name = camp.name,
                        startDate = camp.startDate,
                        endDate = camp.endDate,
                        curriculum = camp.curriculum,
                        schoolId = camp.schoolId
                    )
                }
            }
        }
    }

    override suspend fun getDetailedSchoolInfo(schoolId: String): Flow<DetailedSchool?> {
        return queries.selectDetailedSchoolById(id = schoolId)
            .asFlow()
            .mapToOneOrNull(Dispatchers.IO)
            .map { entity ->

                entity?.let {
                    val county = queries.selectCountyById(id = entity.countyId).executeAsOne()
                    val camps = queries.selectCampBySchoolId(schoolId = entity.id).executeAsList()

                    DetailedSchool(
                        id = entity.id,
                        countyId = entity.countyId,
                        name = entity.name,
                        organizationId = entity.organizationId,
                        county = county.toAppCounty(),
                        camps = camps.map { it.toAppCamp() }
                    )

                }
            }
            .flowOn(Dispatchers.Main)
    }

    override suspend fun insertCounties(counties: List<AppCounty>) {
        queries.transaction {
            counties.forEach { appCounty ->
                queries.insertCounty(
                    id = appCounty.id,
                    name = appCounty.name,
                    latitude = appCounty.latitude,
                    longitude = appCounty.longitude,
                    countryId = appCounty.country?.id ?: "",
                    country = appCounty.country?.name
                )
            }
        }
    }

    override suspend fun insertCounty(appCounty: AppCounty) {
        queries.insertCounty(
            id = appCounty.id,
            name = appCounty.name,
            latitude = appCounty.latitude,
            longitude = appCounty.longitude,
            countryId = appCounty.country?.id ?: "",
            country = appCounty.country?.name
        )
    }

    override suspend fun insertOrganization(appOrganization: AppOrganization) {
        queries.insertOrganization(
            id = appOrganization.id,
            name = appOrganization.name,
            createdAt = appOrganization.createdAt,
            accountType = appOrganization.accountType,
            countryId = appOrganization.countryId
        )
    }

    override suspend fun getOrganizations(): Flow<List<AppOrganization>> {
        return queries.selectAllOrganizations()
            .asFlow()
            .mapToList(Dispatchers.IO)
            .mapNotNull { it.map { organizationEntity -> organizationEntity.toAppOrganization() } }
            .flowOn(Dispatchers.Main)
    }

    override suspend fun insertCamp(appCamp: AppCamp) {
        queries.insertCamp(
            id = appCamp.id,
            name = appCamp.name,
            startDate = appCamp.startDate,
            endDate = appCamp.endDate,
            curriculum = appCamp.curriculum,
            schoolId = appCamp.schoolId
        )
    }

    override suspend fun getCamps(): Flow<List<AppCamp>> {
        return queries.selectAllCamps()
            .asFlow()
            .mapToList(Dispatchers.IO)
            .mapNotNull { it.map { campEntity -> campEntity.toAppCamp() } }
            .flowOn(Dispatchers.Main)
    }

    override suspend fun getDetailedCampById(campId: String): Flow<DetailedCampInfo?> {
        return queries.selectDetailedCampById(campId)
            .asFlow()
            .mapToOneOrNull(Dispatchers.IO)
            .map { it?.toDetailedCampInfo() }
            .flowOn(Dispatchers.Main)
    }

    override suspend fun insertDetailedCampInfo(detailedCamp: DetailedCampInfo) {
        queries.insertDetailedCamp(
            id = detailedCamp.id,
            name = detailedCamp.name,
            startDate = detailedCamp.startDate,
            endDate = detailedCamp.endDate,
            curriculum = detailedCamp.curriculum,
            schoolId = detailedCamp.schoolId,
            organizationId = detailedCamp.organizationId,
            campGroupsSize = detailedCamp.campGroupsSize.toLong(),
            createdAt = detailedCamp.createdAt,
            organizationName = detailedCamp.organization?.name ?: "",
            schoolName = detailedCamp.school?.name ?: "",
            studentsSize = detailedCamp.campGroupsSize.toLong()
        )
    }

    override suspend fun insertStudent(appStudent: AppStudent) {
        queries.insertAppStudent(
            id = appStudent.id,
            firstName = appStudent.firstName,
            lastName = appStudent.lastName,
            age = appStudent.age.toLong(),
            grade = appStudent.grade.toLong(),
            campId = appStudent.campId,
            campName = appStudent.camp.name,
            schoolId = appStudent.camp.schoolId
        )
    }

    override suspend fun insertStudents(appStudents: List<AppStudent>) {
        queries.transaction {
            appStudents.forEach { appStudent ->
                queries.insertAppStudent(
                    id = appStudent.id,
                    firstName = appStudent.firstName,
                    lastName = appStudent.lastName,
                    age = appStudent.age.toLong(),
                    grade = appStudent.grade.toLong(),
                    campId = appStudent.campId,
                    campName = appStudent.camp.name,
                    schoolId = appStudent.camp.schoolId
                )
            }
        }
    }


    override suspend fun getStudents(): Flow<List<AppStudent>> {
        return queries.selectAllAppStudents()
            .asFlow()
            .mapToList(Dispatchers.IO)
            .map { it -> it.map { it.toAppStudents() } }
            .flowOn(Dispatchers.Main)
    }


    override suspend fun getCounties(): Flow<List<AppCounty>> {
        return queries.selectAllCounties()
            .asFlow()
            .mapToList(Dispatchers.IO)
            .mapNotNull { it.map { countyEntity -> countyEntity.toAppCounty() } }
            .flowOn(Dispatchers.Main)
    }


}