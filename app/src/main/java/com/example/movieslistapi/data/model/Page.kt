package com.example.movieslistapi.data.model

import com.google.gson.annotations.SerializedName

data class Page (
    val page: Int,
    val results: Array<PageResult>,
    @SerializedName("total_pages")
    val totalPages: Int
)