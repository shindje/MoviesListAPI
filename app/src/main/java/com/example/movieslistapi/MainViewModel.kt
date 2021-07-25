package com.example.movieslistapi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import com.example.movieslistapi.data.ApiHelper
import com.example.movieslistapi.data.Repository
import com.example.movieslistapi.data.Status
import kotlinx.coroutines.Dispatchers
import java.lang.Exception

class MainViewModel(private val repository: Repository) : ViewModel() {

    fun getMovies(page: Int) = liveData(Dispatchers.IO) {
        emit(Status.LOADING)
        try {
            val success = Status.SUCCESS
            success.data = repository.getPage(page)
            emit(success)
        } catch (e: Exception) {
            val error = Status.ERROR
            error.data = e.localizedMessage
            emit(error)
        }
    }
}

class MainViewModelFactory(private val apiHelper: ApiHelper) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(Repository(apiHelper)) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }

}