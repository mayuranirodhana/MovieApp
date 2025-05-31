class SearchFragment : Fragment() {

    private lateinit var searchView: SearchView
    private lateinit var recyclerView: RecyclerView
    private lateinit var moviesAdapter: MoviesAdapter

    private val movies = mutableListOf<Movie>() // Holds search results

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_search, container, false)

        searchView = view.findViewById(R.id.searchView)
        recyclerView = view.findViewById(R.id.recyclerViewResults)

        moviesAdapter = MoviesAdapter(movies)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = moviesAdapter

        setupSearch()

        return view
    }

    private fun setupSearch() {
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (!query.isNullOrEmpty()) {
                    searchMovies(query)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?) = false
        })
    }

    private fun searchMovies(query: String) {
        // Call ViewModel or repository method here
        // Example (pseudo-code):
        MovieRepository.searchMovies(query) { results ->
            movies.clear()
            movies.addAll(results)
            moviesAdapter.notifyDataSetChanged()
        }
    }
}