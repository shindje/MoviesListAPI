package com.example.movieslistapi.data.model

import com.google.gson.annotations.SerializedName

data class Movie (
    val id: Int,
    val title: String,
    val genres: Array<Genre>,
    val overview: String,
    @SerializedName("poster_path")
    val posterPath: String,
    @SerializedName("release_date")
    val releaseDate: String,
    val runtime: Int,
    @SerializedName("vote_average")
    val voteAverage: Double
)