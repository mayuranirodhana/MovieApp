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

class MoviesAdapter(private val movies: List<Movie>) :
    RecyclerView.Adapter<MoviesAdapter.MovieViewHolder>() {

    // Static genre ID to name map
    private val genreMap = mapOf(
        28 to "Action",
        12 to "Adventure",
        14 to "Fantasy",
        16 to "Animation",
        35 to "Comedy",
        80 to "Crime",
        18 to "Drama",
        27 to "Horror",
        10749 to "Romance",
        53 to "Thriller",
        878 to "Sci-Fi",
        10751 to "Family"
    )

    class MovieViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.textViewTitle)
        val poster: ImageView = view.findViewById(R.id.imageViewPoster)
        val genreYear: TextView = view.findViewById(R.id.textViewGenreYear)
        val rating: TextView = view.findViewById(R.id.textViewRating)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_movie, parent, false)
        return MovieViewHolder(view)
    }

    override fun getItemCount(): Int = movies.size

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = movies[position]

        // Set title
        holder.title.text = movie.title

        // Load poster using Glide
        Glide.with(holder.view)
            .load("https://image.tmdb.org/t/p/w500${movie.posterPath}")
            .placeholder(R.drawable.sample_movie)
            .into(holder.poster)

        // Map genre IDs to names (limit to 2)
        val genres = movie.genreIds.mapNotNull { genreMap[it] }.take(2).joinToString(" · ")

        // Extract year from releaseDate
        val year = if (movie.releaseDate.length >= 4) movie.releaseDate.substring(0, 4) else ""

        // Combine genres and year
        holder.genreYear.text = "$genres · $year"

        // Set rating
        holder.rating.text = String.format("%.1f", movie.voteAverage)
    }
}