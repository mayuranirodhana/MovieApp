package com.mayura.movieapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mayura.movieapp.R
import com.mayura.movieapp.data.model.Genre
import com.mayura.movieapp.data.model.Movie
import com.mayura.movieapp.databinding.ItemMovieBinding

class MovieAdapter(
    private val onItemClick: (Movie) -> Unit
) : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    private var movieList = listOf<Movie>()
    private var genreList = listOf<Genre>()

    fun submitList(movies: List<Movie>) {
        movieList = movies
        notifyDataSetChanged()
    }

    // Setter for genre list
    fun setGenres(genres: List<Genre>) {
        genreList = genres
        notifyDataSetChanged() // Refresh to show genre names
    }

    inner class MovieViewHolder(private val binding: ItemMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(movie: Movie) {
            binding.movieTitle.text = movie.title
            binding.movieYear.text = movie.releaseDate.take(4)

            // Map genre IDs to names
            val genreNames = movie.genreIds.mapNotNull { id ->
                genreList.find { it.id == id }?.name
            }.joinToString(", ")
            binding.movieGenres.text = genreNames

            val imageUrl = "https://image.tmdb.org/t/p/w500${movie.posterPath}"
            Glide.with(binding.moviePoster.context)
                .load(imageUrl)
                .placeholder(R.drawable.sample_movie)
                .error(R.drawable.sample_movie)
                .into(binding.moviePoster)

            // Bind rating
            binding.textViewRating.text = String.format("%.1f", movie.voteAverage)

            binding.root.setOnClickListener {
                onItemClick.invoke(movie)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(movieList[position])
    }

    override fun getItemCount(): Int = movieList.size
}
