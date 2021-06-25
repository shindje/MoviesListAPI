package com.example.movieslistapi

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupViewModel()
        setupUI()
        setupObservers()
    }

    private fun setupViewModel() {
        viewModel = ViewModelProviders.of(
            this,
            ViewModelFactory(ApiHelper(RetrofitBuilder.apiService))
        ).get(MainViewModel::class.java)
    }

    private fun setupUI() {
        recyclerView.layoutManager = GridLayoutManager(this,2)
        adapter = MainAdapter(arrayListOf())
        recyclerView.adapter = adapter
    }

    private fun setupObservers() {
        viewModel.getUsers(1).observe(this, Observer {
            it?.let { status ->
                when (status) {
                    Status.SUCCESS -> {
                        pb.visibility = View.GONE
                        val page = status.data as Page
                        retrieveList(page.results)
                        recyclerView.visibility = View.VISIBLE
                    }
                    Status.LOADING -> {
                        recyclerView.visibility = View.GONE
                        pb.visibility = View.VISIBLE
                    }
                    Status.ERROR -> {
                        recyclerView.visibility = View.GONE
                        pb.visibility = View.VISIBLE
                        Toast.makeText(this, status.data as String, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })
    }

    private fun retrieveList(results: Array<PageResult>) {
        adapter.apply {
            addResults(results)
            notifyDataSetChanged()
        }
    }
}