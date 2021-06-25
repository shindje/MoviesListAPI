package com.example.movieslistapi

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movieslistapi.data.model.PageResult
import kotlinx.android.synthetic.main.rv_item.view.*

class MainAdapter (private val users: ArrayList<PageResult>) : RecyclerView.Adapter<MainAdapter.DataViewHolder>() {

    class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(pageResult: PageResult) {
            itemView.apply {
                tvTitle.text = pageResult.title
                tvVote.text = pageResult.voteAverage.toString()
                tvDate.text = pageResult.releaseDate
                Glide.with(ivPoster.context)
                    .load("https://image.tmdb.org/t/p/w500" + pageResult.posterPath)
                    .into(ivPoster)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder =
        DataViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.rv_item, parent, false))

    override fun getItemCount(): Int = users.size

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.bind(users[position])
    }

    fun addResults(pageResult: Array<PageResult>, clear: Boolean) {
        this.users.apply {
            if (clear)
                clear()
            addAll(pageResult)
        }

    }
}