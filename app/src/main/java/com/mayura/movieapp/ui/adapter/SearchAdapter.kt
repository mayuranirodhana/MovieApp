package com.mayura.movieapp.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mayura.movieapp.R
import com.mayura.movieapp.data.model.Movie
import com.mayura.movieapp.databinding.ItemSearchResultBinding


class SearchAdapter(
    private val onItemClick: (Movie) -> Unit
) : ListAdapter<Movie, SearchAdapter.SearchViewHolder>(DiffCallback()) {

    inner class SearchViewHolder(private val binding: ItemSearchResultBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(movie: Movie) {
            val year = movie.releaseDate.take(4).ifEmpty { "N/A" }
            binding.textViewTitle.text = "${movie.title} ($year)"

            Glide.with(binding.imageViewThumbnail.context)
                .load("https://image.tmdb.org/t/p/w185${movie.posterPath}")
                .error(R.drawable.sample_movie)
                .into(binding.imageViewThumbnail)

            // Handle item click
            binding.root.setOnClickListener {
                onItemClick(movie)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val binding = ItemSearchResultBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SearchViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class DiffCallback : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Movie, newItem: Movie) = oldItem == newItem
    }
}