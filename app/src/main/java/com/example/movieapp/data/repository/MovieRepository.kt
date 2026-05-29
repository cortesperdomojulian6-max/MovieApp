package com.example.movieapp.data.repository

import com.example.movieapp.data.api.RetrofitClient
import com.example.movieapp.data.local.FavoriteMovie
import com.example.movieapp.data.local.MovieDatabase
import com.example.movieapp.data.model.Movie
import com.example.movieapp.data.model.MovieDetail
import com.example.movieapp.data.model.MovieVideo
import kotlinx.coroutines.flow.Flow

class MovieRepository(private val database: MovieDatabase) {

    private val api = RetrofitClient.tmdbApi
    private val dao = database.favoriteMovieDao()

    suspend fun getPopularMovies(page: Int = 1): Result<List<Movie>> {
        return try {
            val response = api.getPopularMovies(page = page)
            Result.success(response.results)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getMovieDetail(movieId: Int): Result<MovieDetail> {
        return try {
            val detail = api.getMovieDetail(movieId)
            Result.success(detail)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    fun getAllFavorites(): Flow<List<FavoriteMovie>> {
        return dao.getAllFavorites()
    }

    suspend fun searchMovies(query: String, page: Int = 1): Result<List<Movie>> {
        return try {
            val response = api.searchMovies(query = query, page = page)
            Result.success(response.results)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getMovieTrailer(movieId: Int): Result<MovieVideo?> {
        return try {
            val videos = api.getMovieVideos(movieId)
            val trailer = videos.results.firstOrNull {
                it.site == "YouTube" && it.type == "Trailer"
            }
            Result.success(trailer)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    fun isFavorite(movieId: Int): Flow<Boolean> {
        return dao.isFavorite(movieId)
    }

    suspend fun addFavorite(movie: Movie) {
        val favorite = FavoriteMovie(
            id = movie.id,
            title = movie.title,
            posterPath = movie.posterPath,
            overview = movie.overview,
            voteAverage = movie.voteAverage,
            releaseDate = movie.releaseDate
        )
        dao.insert(favorite)
    }

    suspend fun removeFavorite(movieId: Int) {
        val favorite = dao.getFavoriteById(movieId)
        if (favorite != null) {
            dao.delete(favorite)
        }
    }

    suspend fun toggleFavorite(movie: Movie) {
        val existing = dao.getFavoriteById(movie.id)
        if (existing != null) {
            dao.delete(existing)
        } else {
            addFavorite(movie)
        }
    }
}
