package com.example.movieapp.ui.navigation

sealed class Screen(val route: String) {
    data object Splash : Screen("splash")
    data object MovieList : Screen("movie_list")
    data object Favorites : Screen("favorites")
    data object Settings : Screen("settings")
    data object MovieDetail : Screen("movie_detail/{movieId}") {
        fun createRoute(movieId: Int) = "movie_detail/$movieId"
    }
}
