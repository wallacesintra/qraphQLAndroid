package com.example.graphqlandroid.domain.models

data class Results<out T>(val status: ResultStatus,val data: T?, val message: String?){
    companion object {
        fun <T> initial(): Results<T> {
            return Results(status = ResultStatus.INITIAL, data = null, message = null)
        }

        fun <T> success(data: T): Results<T & Any> {
            return Results(status = ResultStatus.SUCCESS, data = data, message = "data fetched successful")
        }

        fun <T> error(msg: String = "Something went wrong. We are working on it"): Results<T> {
            return Results(ResultStatus.ERROR, null, msg)
        }

        fun <T> loading(): Results<T> {
            return Results(ResultStatus.LOADING, null, null)
        }
    }
}