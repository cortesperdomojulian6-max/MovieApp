package com.example.movieapp.ui.screens.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.movieapp.data.model.MovieDetail
import com.example.movieapp.data.repository.MovieRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class MovieDetailUiState(
    val movieDetail: MovieDetail? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
    val isFavorite: Boolean = false
)

class MovieDetailViewModel(
    private val repository: MovieRepository,
    private val movieId: Int
) : ViewModel() {

    private val _uiState = MutableStateFlow(MovieDetailUiState())
    val uiState: StateFlow<MovieDetailUiState> = _uiState.asStateFlow()

    init {
        loadMovieDetail()
        observeFavoriteStatus()
    }

    private fun loadMovieDetail() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)

            val result = repository.getMovieDetail(movieId)
            result.fold(
                onSuccess = { detail ->
                    _uiState.value = _uiState.value.copy(
                        movieDetail = detail,
                        isLoading = false
                    )
                },
                onFailure = { exception ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = exception.message ?: "Error al cargar detalle"
                    )
                }
            )
        }
    }

    private fun observeFavoriteStatus() {
        viewModelScope.launch {
            repository.isFavorite(movieId).collect { isFav ->
                _uiState.value = _uiState.value.copy(isFavorite = isFav)
            }
        }
    }

    fun toggleFavorite() {
        viewModelScope.launch {
            val detail = _uiState.value.movieDetail ?: return@launch
            val movie = com.example.movieapp.data.model.Movie(
                id = detail.id,
                title = detail.title,
                posterPath = detail.posterPath,
                overview = detail.overview,
                voteAverage = detail.voteAverage,
                releaseDate = detail.releaseDate,
                backdropPath = detail.backdropPath,
                genreIds = null
            )
            repository.toggleFavorite(movie)
        }
    }

    class Factory(
        private val repository: MovieRepository,
        private val movieId: Int
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return MovieDetailViewModel(repository, movieId) as T
        }
    }
}
