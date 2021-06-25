package com.example.movieslistapi

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.example.movieslistapi.data.ApiHelper
import com.example.movieslistapi.data.RetrofitBuilder
import com.example.movieslistapi.data.Status
import com.example.movieslistapi.data.model.Movie
import kotlinx.android.synthetic.main.activity_details.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.rv_item.view.*

class DetailsActivity : AppCompatActivity() {
    private lateinit var viewModel: DetailsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        setupViewModel()
        intent.extras?.apply {
            val id: Int = this.getInt("id")
            setupObservers(id)
        }
    }

    private fun setupViewModel() {
        viewModel = ViewModelProviders.of(
            this,
            DetailsViewModelFactory(ApiHelper(RetrofitBuilder.apiService))
        ).get(DetailsViewModel::class.java)
    }

    private fun setupObservers(id: Int) {
        viewModel.getMovieInfo(id).observe(this, Observer {
            it?.let { status ->
                when (status) {
                    Status.SUCCESS -> {
                        pb_details.visibility = View.GONE
                        val movie = status.data as Movie
                        showDetails(movie)
                    }
                    Status.LOADING -> {
                        pb_details.visibility = View.VISIBLE
                    }
                    Status.ERROR -> {
                        pb_details.visibility = View.GONE
                        Toast.makeText(this, status.data as String, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })
    }

    private fun showDetails(movie: Movie) {
        details_title.text = movie.title
        Glide.with(details_iv_poster.context)
            .load("https://image.tmdb.org/t/p/w500" + movie.posterPath)
            .into(details_iv_poster)
        details_date.text = movie.releaseDate
        details_runtime.text = "${movie.runtime} мин"
        details_genres.text = movie.genres.joinToString(transform = {genre -> return@joinToString genre.name })
        details_vote.text = movie.voteAverage.toString()
        details_overview.text = movie.overview
    }
}