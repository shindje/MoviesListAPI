package com.example.movieslistapi

import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.example.movieslistapi.data.ApiHelper
import com.example.movieslistapi.data.RetrofitBuilder
import com.example.movieslistapi.data.Status
import com.example.movieslistapi.data.model.Page
import com.example.movieslistapi.data.model.PageResult
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: MainAdapter
    private var page = 1
    private var totalPages = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupViewModel()
        setupUI()
        setupObservers()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.toolbar_menu, menu)

        return super.onCreateOptionsMenu(menu)
    }

    private fun setupViewModel() {
        viewModel = ViewModelProviders.of(
            this,
            ViewModelFactory(ApiHelper(RetrofitBuilder.apiService))
        ).get(MainViewModel::class.java)
    }

    private fun setupUI() {
        recyclerView.layoutManager = GridLayoutManager(this, 2)
        adapter = MainAdapter(arrayListOf())
        recyclerView.adapter = adapter

        fabLoadMore.setOnClickListener { loadMore() }
    }

    private fun setupObservers() {
        viewModel.getUsers(page).observe(this, Observer {
            it?.let { status ->
                when (status) {
                    Status.SUCCESS -> {
                        pb.visibility = View.GONE
                        val pageData = status.data as Page
                        page = pageData.page
                        totalPages = pageData.totalPages
                        retrieveList(pageData.results)
                    }
                    Status.LOADING -> {
                        pb.visibility = View.VISIBLE
                    }
                    Status.ERROR -> {
                        pb.visibility = View.VISIBLE
                        Toast.makeText(this, status.data as String, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })
    }

    private fun retrieveList(results: Array<PageResult>) {
        adapter.apply {
            addResults(results, page == totalPages)
            notifyDataSetChanged()
        }
    }

    private fun loadMore() {
        if (page >= totalPages)
            Toast.makeText(this, "Загружена последняя страница", Toast.LENGTH_SHORT).show()
        else {
            page++
            setupObservers()
        }
    }
}