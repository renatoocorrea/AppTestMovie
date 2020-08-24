package com.renatooc.apptdc.ui.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.renatooc.apptdc.R
import com.renatooc.apptdc.data.model.Movie
import kotlinx.android.synthetic.main.movie_item.view.*

class MainAdapter(
    private val users: ArrayList<Movie>
) : RecyclerView.Adapter<MainAdapter.DataViewHolder>() {

    class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(user: Movie) {
            itemView.movie_name.text = user.title
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        DataViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.movie_item, parent,
                false
            )
        )

    override fun getItemCount(): Int = users.size

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) =
        holder.bind(users[position])

    fun addData(list: List<Movie>) {
        users.addAll(list)
    }

}