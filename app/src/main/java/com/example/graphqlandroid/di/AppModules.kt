package com.example.graphqlandroid.di

import com.apollographql.apollo3.ApolloClient
import com.example.graphqlandroid.AppDatabase
import com.example.graphqlandroid.data.local.DatabaseDriverFactory
import com.example.graphqlandroid.data.local.DatabaseSource
import com.example.graphqlandroid.data.local.DatabaseSourceImpl
import com.example.graphqlandroid.data.network.httpUrlBuilder
import com.example.graphqlandroid.data.remote.RemoteRepository
import com.example.graphqlandroid.data.remote.RemoteRepositoryImpl
import com.example.graphqlandroid.domain.viewmodels.authentication.LoginViewModel
import com.example.graphqlandroid.domain.viewmodels.authentication.FirstPageViewModel
import com.example.graphqlandroid.domain.viewmodels.HomeViewModel
import com.example.graphqlandroid.presentation.common.SnackBarHandler
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val appModules = module {
    single<ApolloClient> {
        ApolloClient.Builder()
            .serverUrl(httpUrlBuilder())
            .build()
    }

    single<AppDatabase> {
        AppDatabase(driver = get<DatabaseDriverFactory>().create())
    }

    single<DatabaseDriverFactory> {
        DatabaseDriverFactory(context = get())
    }
    
    single<DatabaseSource> { 
        DatabaseSourceImpl(
            database = get()
        )
    }

    single<RemoteRepository> {
        RemoteRepositoryImpl(
            apolloClient = get()
        )
    }

    single<SnackBarHandler> {
        SnackBarHandler()
    }

    viewModelOf(::LoginViewModel)

    viewModelOf(::FirstPageViewModel)

    viewModelOf(::HomeViewModel)

}