package com.mayura.movieapp.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mayura.movieapp.R
import com.mayura.movieapp.data.model.Movie

class SearchAdapter(private var movies: List<Movie>) :
    RecyclerView.Adapter<SearchAdapter.SearchViewHolder>() {

    fun updateData(newMovies: List<Movie>) {
        movies = newMovies
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_search_result, parent, false)
        return SearchViewHolder(view)
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        holder.bind(movies[position])
    }

    override fun getItemCount(): Int = movies.size

    inner class SearchViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageViewPoster: ImageView = itemView.findViewById(R.id.imageViewPoster)
        private val textViewTitle: TextView = itemView.findViewById(R.id.textViewTitle)
        private val textViewGenreYear: TextView = itemView.findViewById(R.id.textViewGenreYear)
        private val textViewRating: TextView = itemView.findViewById(R.id.textViewRating)

        fun bind(movie: Movie) {
            textViewTitle.text = movie.title
            textViewGenreYear.text = movie.releaseDate.take(4) // e.g., 2024
            textViewRating.text = String.format("%.1f", movie.voteAverage)

            Glide.with(itemView.context)
                .load("https://image.tmdb.org/t/p/w500${movie.posterPath}")
                .placeholder(R.drawable.sample_movie)
                .into(imageViewPoster)
        }
    }
}