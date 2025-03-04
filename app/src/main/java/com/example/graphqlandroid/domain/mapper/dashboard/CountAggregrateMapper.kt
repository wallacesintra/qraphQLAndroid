package com.example.graphqlandroid.domain.mapper.dashboard

import com.example.GetCountsQuery
import com.example.graphqlandroid.domain.models.CountAggregrate
import database.CountAggregrateEntity

fun GetCountsQuery.Data.toCountAggregrate(): CountAggregrate{
    return CountAggregrate(
        studentsCount = students?.size ?: 0,
        schoolsCount = schools?.size ?: 0,
        campsCount = camps?.size ?: 0
    )
}

fun CountAggregrateEntity.toCountAggregrate(): CountAggregrate {
    return CountAggregrate(
        studentsCount = studentsCount.toInt(),
        schoolsCount = schoolsCount.toInt(),
        campsCount = campsCount.toInt()
    )
}