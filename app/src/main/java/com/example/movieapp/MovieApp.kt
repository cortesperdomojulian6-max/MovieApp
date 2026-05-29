package com.example.movieapp

import android.app.Application
import android.content.Context
import com.example.movieapp.data.local.MovieDatabase
import com.example.movieapp.data.repository.MovieRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class MovieApp : Application() {

    lateinit var repository: MovieRepository
        private set

    private val _isDarkMode = MutableStateFlow(true)
    val isDarkMode: StateFlow<Boolean> = _isDarkMode.asStateFlow()

    override fun onCreate() {
        super.onCreate()
        val database = MovieDatabase.getInstance(this)
        repository = MovieRepository(database)

        val prefs = getSharedPreferences("settings", Context.MODE_PRIVATE)
        _isDarkMode.value = prefs.getBoolean("dark_mode", true)
    }

    fun setDarkMode(enabled: Boolean) {
        _isDarkMode.value = enabled
        getSharedPreferences("settings", Context.MODE_PRIVATE)
            .edit()
            .putBoolean("dark_mode", enabled)
            .apply()
    }
}
