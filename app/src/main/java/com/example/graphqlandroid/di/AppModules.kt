package com.example.graphqlandroid.di

import com.apollographql.apollo3.ApolloClient
import com.example.graphqlandroid.data.network.httpUrlBuilder
import com.example.graphqlandroid.data.remote.RemoteRepository
import com.example.graphqlandroid.data.remote.RemoteRepositoryImpl
import com.example.graphqlandroid.domain.viewmodels.authentication.LoginViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val appModules = module {
    single<ApolloClient> {
        ApolloClient.Builder()
            .serverUrl(httpUrlBuilder())
            .build()
    }

    single<RemoteRepository> {
        RemoteRepositoryImpl(
            apolloClient = get()
        )
    }

    viewModelOf(::LoginViewModel)

}