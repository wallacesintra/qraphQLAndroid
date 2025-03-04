package com.example.graphqlandroid.data.local

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import app.cash.sqldelight.coroutines.mapToOneOrNull
import com.example.graphqlandroid.AppDatabase
import com.example.graphqlandroid.domain.mapper.dashboard.toCountAggregrate
import com.example.graphqlandroid.domain.mapper.school.toSchool
import com.example.graphqlandroid.domain.mapper.toAppCamp
import com.example.graphqlandroid.domain.mapper.toAppCounty
import com.example.graphqlandroid.domain.mapper.toUser
import com.example.graphqlandroid.domain.models.AppUser
import com.example.graphqlandroid.domain.models.CountAggregrate
import com.example.graphqlandroid.domain.models.school.AppSchool
import com.example.graphqlandroid.domain.models.school.DetailedSchool
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
            .map { it.map { schoolEntity -> schoolEntity.toSchool() }}
            .flowOn(Dispatchers.Main)
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
        return queries.selectDetailedSchoolById(id = schoolId )
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

}