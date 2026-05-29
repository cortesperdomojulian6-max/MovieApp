package com.example.movieapp.ui.screens.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.movieapp.data.local.FavoriteMovie
import com.example.movieapp.data.model.Movie
import com.example.movieapp.data.repository.MovieRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class MovieListUiState(
    val movies: List<Movie> = emptyList(),
    val favoriteMovies: List<FavoriteMovie> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val favoriteIds: Set<Int> = emptySet(),
    val searchQuery: String = "",
    val searchResults: List<Movie> = emptyList(),
    val isSearching: Boolean = false,
    val isSearchActive: Boolean = false
)

class MovieListViewModel(
    private val repository: MovieRepository,
    private val showFavoritesOnly: Boolean = false
) : ViewModel() {

    private val _uiState = MutableStateFlow(MovieListUiState())
    val uiState: StateFlow<MovieListUiState> = _uiState.asStateFlow()

    private var searchJob: Job? = null

    init {
        loadMovies()
        observeFavorites()
    }

    fun refresh() {
        loadMovies()
    }

    private fun loadMovies() {
        if (showFavoritesOnly) return

        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)

            val result = repository.getPopularMovies()
            result.fold(
                onSuccess = { movies ->
                    _uiState.value = _uiState.value.copy(
                        movies = movies,
                        isLoading = false
                    )
                },
                onFailure = { exception ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = exception.message ?: "Error al cargar películas"
                    )
                }
            )
        }
    }

    fun onSearchQueryChanged(query: String) {
        _uiState.value = _uiState.value.copy(searchQuery = query, isSearchActive = query.isNotBlank())
        searchJob?.cancel()
        if (query.isBlank()) {
            _uiState.value = _uiState.value.copy(searchResults = emptyList(), isSearching = false)
            return
        }
        searchJob = viewModelScope.launch {
            delay(400)
            _uiState.value = _uiState.value.copy(isSearching = true)
            val result = repository.searchMovies(query)
            result.fold(
                onSuccess = { movies ->
                    _uiState.value = _uiState.value.copy(
                        searchResults = movies,
                        isSearching = false
                    )
                },
                onFailure = {
                    _uiState.value = _uiState.value.copy(isSearching = false)
                }
            )
        }
    }

    fun clearSearch() {
        _uiState.value = _uiState.value.copy(
            searchQuery = "",
            searchResults = emptyList(),
            isSearchActive = false,
            isSearching = false
        )
        searchJob?.cancel()
    }

    private fun observeFavorites() {
        viewModelScope.launch {
            repository.getAllFavorites().collect { favorites ->
                _uiState.value = _uiState.value.copy(
                    favoriteMovies = favorites,
                    favoriteIds = favorites.mapTo(mutableSetOf()) { it.id }
                )
            }
        }
    }

    fun toggleFavorite(movie: Movie) {
        viewModelScope.launch {
            repository.toggleFavorite(movie)
        }
    }

    class Factory(
        private val repository: MovieRepository,
        private val showFavoritesOnly: Boolean = false
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return MovieListViewModel(repository, showFavoritesOnly) as T
        }
    }
}
