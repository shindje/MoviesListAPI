package com.example.movieslistapi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import com.example.movieslistapi.data.ApiHelper
import com.example.movieslistapi.data.Repository
import com.example.movieslistapi.data.Status
import kotlinx.coroutines.Dispatchers
import java.lang.Exception

class DetailsViewModel(private val repository: Repository) : ViewModel() {

    fun getMovieInfo(id: Int) = liveData(Dispatchers.IO) {
        emit(Status.LOADING)
        try {
            val success = Status.SUCCESS
            success.data = repository.getMovieInfo(id)
            emit(success)
        } catch (e: Exception) {
            val error = Status.ERROR
            error.data = e.localizedMessage
            emit(error)
        }
    }
}

class DetailsViewModelFactory(private val apiHelper: ApiHelper) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailsViewModel::class.java)) {
            return DetailsViewModel(Repository(apiHelper)) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }

}