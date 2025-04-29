package com.example.jettrivia.data

data class DataOrException<T, boolean, E: Exception>(
    var data: T? = null,
    var loading: boolean? = null,
    var e: E? = null
)
