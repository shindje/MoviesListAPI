package com.example.movieslistapi.data

val API_KEY = "274f828ad283bd634ef4fc1ee4af255f"

class ApiHelper(private val apiService: ApiService) {

    suspend fun getPage(page: Int) =
        apiService.getPage(
            page,
            API_KEY,
            "vote_average.desc",
            300,
            "ru-Ru"
        )

    suspend fun getMovieInfo(id: Int) =
        apiService.getMovieInfo(
            id,
            API_KEY,
            "ru-Ru"
        )
}