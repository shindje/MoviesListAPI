package com.example.movieslistapi.data

enum class Status {
    SUCCESS,
    ERROR,
    LOADING;

    var data: Any? = null
}