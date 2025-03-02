package com.example.graphqlandroid.data.network

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.Query
import java.lang.reflect.TypeVariable

class Http {
    fun  apolloClient(query: Query<*>): ApolloClient{
        val client = ApolloClient
            .builder()
            .serverUrl(httpUrlBuilder())
            .build()

        client.query(query = query)
        return client
    }

}