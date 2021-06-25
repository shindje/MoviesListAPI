package com.example.movieslistapi.data

import com.example.movieslistapi.data.model.Movie
import com.example.movieslistapi.data.model.Page
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("discover/movie")
    suspend fun getPage(
        @Query("page") page: Int,
        @Query("api_key") apiKey: String,
        @Query("sort_by") sortBy: String,
        @Query("vote_count.gte") voteCountGte: Int,
        @Query("language") language: String
    ): Page

    @GET("movie/{id}")
    suspend fun getMovieInfo(
        @Path("id") id: Int,
        @Query("api_key") apiKey: String,
        @Query("language") language: String
    ): Movie
}