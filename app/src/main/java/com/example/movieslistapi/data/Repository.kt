package com.example.movieslistapi.data

class Repository (private val apiHelper: ApiHelper) {

    suspend fun getPage(page: Int) = apiHelper.getPage(page)
}