package com.example.graphqlandroid.data.network

private const val URL = "10.0.2.2:4200/graphql"

fun httpUrlBuilder(): String {
    return "http://$URL"
}