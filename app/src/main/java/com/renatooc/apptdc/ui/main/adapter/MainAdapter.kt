package com.renatooc.apptdc.ui.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.renatooc.apptdc.R
import com.renatooc.apptdc.data.model.Movie
import com.renatooc.apptdc.util.Constants.Companion.MOVIE_BASE_URL
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.movie_item.view.*

class MainAdapter(
    private val users: ArrayList<Movie>
) : RecyclerView.Adapter<MainAdapter.DataViewHolder>() {

    class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(movie: Movie) {
            val releaseData = itemView.movie_release_data
            val imageDescription = itemView.movie_image
            val movieName = itemView.movie_name

            releaseData.text = movie.release_date
            movieName.text  = movie.title

            Picasso.get().load(MOVIE_BASE_URL + movie.poster_path)
                .placeholder(R.drawable.ic_launcher_background)
                .into(imageDescription)
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

    fun addData(list: MutableList<Movie>?) {
        if (list != null) {
            users.addAll(list)
        }
    }

}