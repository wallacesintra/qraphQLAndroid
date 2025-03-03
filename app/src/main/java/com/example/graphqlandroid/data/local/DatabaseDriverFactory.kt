package com.example.graphqlandroid.data.local

import android.content.Context
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.example.graphqlandroid.AppDatabase


class DatabaseDriverFactory(
    private val context: Context
) {

    fun create(): SqlDriver {
        val driver = AndroidSqliteDriver(
            AppDatabase.Schema,
            context,
            "appDatabase.sq"
        )

        driver.execute(null, "PRAGMA foreign_keys=OFF;", 0)

        return driver
    }

}