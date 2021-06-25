package com.example.movieslistapi.data.model

data class Page (
    val page: Int,
    val results: Array<PageResult>
)