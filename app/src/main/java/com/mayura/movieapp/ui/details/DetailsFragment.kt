package com.mayura.movieapp.ui.details

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.mayura.movieapp.R
import com.mayura.movieapp.data.model.Genre
import com.mayura.movieapp.data.model.Movie
import com.mayura.movieapp.databinding.FragmentDetailsBinding
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

class DetailsFragment : Fragment() {

    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!

    private var movie: Movie? = null
    private var genreList: List<Genre>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        movie = arguments?.getParcelable("movie")
        genreList = arguments?.getParcelableArrayList("genres")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        movie?.let { movie ->
            binding.movieTitle.text = movie.title
            binding.movieOverview.text = movie.overview

            // Format date
            binding.movieRelease.text = "Release: ${formatDate(movie.releaseDate)}"

            // Bind numeric rating
            binding.textViewRating.text = String.format("%.1f", movie.voteAverage)

            // Optional: bind to RatingBar (scale 10 -> 5 stars)
            binding.movieRatingBar.rating = (movie.voteAverage / 2).toFloat()

            // Map genre IDs to genre names
            val genresFormatted = movie.genreIds.mapNotNull { id ->
                genreList?.find { it.id == id }?.name
            }.joinToString(", ")
            binding.movieGenres.text = "Genres: $genresFormatted"

            Glide.with(this)
                .load("https://image.tmdb.org/t/p/w500${movie.posterPath}")
                .placeholder(R.drawable.img_default)
                .error(R.drawable.img_default)
                .into(binding.moviePoster)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun formatDate(dateString: String): String {
        return try {
            val parsedDate = LocalDate.parse(dateString)
            parsedDate.format(DateTimeFormatter.ofPattern("MMM dd, yyyy", Locale.US))
        } catch (e: Exception) {
            dateString
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
