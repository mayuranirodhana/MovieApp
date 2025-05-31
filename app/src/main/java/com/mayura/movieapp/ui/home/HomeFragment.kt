package com.mayura.movieapp.ui.home

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.mayura.movieapp.R
import com.mayura.movieapp.ui.adapter.MoviesAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    private val viewModel: HomeViewModel by viewModels()

    private lateinit var btnAll: Chip
    private lateinit var btnAction: Chip
    private lateinit var btnComedy: Chip
    private lateinit var btnDrama: Chip

    private lateinit var recyclerView: RecyclerView
    private val apiKey = "14f7c50d6a82c630262cf1de7f4b85ca"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        recyclerView = view.findViewById(R.id.recyclerViewMovies)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Initialize buttons
        btnAll = view.findViewById(R.id.chipAll)
        btnAction = view.findViewById(R.id.chipAction)
        btnComedy = view.findViewById(R.id.chipComedy)
        btnDrama = view.findViewById(R.id.chipDrama)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Observe movies
        viewModel.movies.observe(viewLifecycleOwner, Observer { movieList ->
            recyclerView.adapter = MoviesAdapter(movieList)
        })

        // Load all movies
        viewModel.loadMovies(apiKey)

        // Set default selection to "All"
        selectButton(btnAll)
        viewModel.filterByGenre("All")

        // Set filter listeners
        btnAll.setOnClickListener {
            selectButton(it)
            viewModel.filterByGenre("All")
        }

        btnAction.setOnClickListener {
            selectButton(it)
            viewModel.filterByGenre("Action")
        }

        btnComedy.setOnClickListener {
            selectButton(it)
            viewModel.filterByGenre("Comedy")
        }

        btnDrama.setOnClickListener {
            selectButton(it)
            viewModel.filterByGenre("Drama")
        }
    }

    private fun selectButton(selected: View) {
        val buttons = listOf(btnAll, btnAction, btnComedy, btnDrama)
        buttons.forEach { it.isChecked = false }
        (selected as? MaterialButton)?.isChecked = true
    }
}