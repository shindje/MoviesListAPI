package com.example.movieslistapi

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.movieslistapi.data.ApiHelper
import com.example.movieslistapi.data.Status
import com.example.movieslistapi.data.model.Movie
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class DetailsViewModelTest {
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var testCoroutineRule = TestCoroutineRule()

    private lateinit var detailsViewModel: DetailsViewModel

    @Mock
    private lateinit var apiHelper: ApiHelper

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        detailsViewModel = DetailsViewModelFactory(apiHelper).create(DetailsViewModel::class.java)
    }

    @Test
    fun liveData_TestReturnValueIsNotNull() {
        testCoroutineRule.runBlockingTest {
            val observer = Observer<Status> {}
            val liveData = detailsViewModel.getMovieInfo(1)
            try {
                liveData.observeForever(observer)
                //waiting for any data
                while (liveData.value == null) {}
                Assert.assertNotNull(liveData.value)
            } finally {
                liveData.removeObserver(observer)
            }
        }
    }

    @Test
    fun liveData_TestReturnLoadingAsFirstValue() {
        testCoroutineRule.runBlockingTest {
            val observer = Observer<Status> {}
            val liveData = detailsViewModel.getMovieInfo(1)
            try {
                liveData.observeForever(observer)
                //waiting for first data
                while (liveData.value == null) {}
                Assert.assertEquals(liveData.value, Status.LOADING)
            } finally {
                liveData.removeObserver(observer)
            }
        }
    }


    @Test
    fun liveData_TestReturnMockedValue() {
        testCoroutineRule.runBlockingTest {
            Mockito.`when`(apiHelper.getMovieInfo(1)).thenReturn(Movie(1, "", arrayOf(), "", "", "", 0, 0.0))

            val observer = Observer<Status> {}
            val liveData = detailsViewModel.getMovieInfo(1)
            try {
                liveData.observeForever(observer)
                //waiting for first not LOADING data
                while (liveData.value == null || liveData.value == Status.LOADING) {}
                val movieData = liveData.value!!.data as Movie
                Assert.assertEquals(movieData.id, 1)
            } finally {
                liveData.removeObserver(observer)
            }
        }
    }

    @Test
    fun liveData_TestReturnError() {
        testCoroutineRule.runBlockingTest {
            Mockito.`when`(apiHelper.getMovieInfo(1)).thenThrow(RuntimeException())

            val observer = Observer<Status> {}
            val liveData = detailsViewModel.getMovieInfo(1)
            try {
                liveData.observeForever(observer)
                //waiting for first not LOADING data
                while (liveData.value == null || liveData.value == Status.LOADING) {}
                Assert.assertEquals(liveData.value, Status.ERROR)
            } finally {
                liveData.removeObserver(observer)
            }
        }
    }
}