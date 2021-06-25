package com.example.movieslistapi.data.model

import com.google.gson.annotations.SerializedName

data class PageResult (
    val id: Int,
    val title: String,
    @SerializedName("vote_average")
    val voteAverage: Double,
    @SerializedName("poster_path")
    val posterPath: String,
    @SerializedName("release_date")
    val releaseDate: String
)