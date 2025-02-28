package com.example.graphqlandroid

import com.example.graphqlandroid.di.appModules
import org.junit.Test
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.test.verify.verify

class CheckModulesTest {

    @OptIn(KoinExperimentalAPI::class)
    @Test
    fun checkAllModules(){
        appModules.verify()
    }
}