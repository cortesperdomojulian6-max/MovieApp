package com.example.movieapp.data.model

import com.google.gson.annotations.SerializedName

data class MovieVideosResponse(
    val id: Int,
    val results: List<MovieVideo>
)

data class MovieVideo(
    val id: String,
    val key: String,
    val name: String,
    val site: String,
    val type: String,
    @SerializedName("official")
    val official: Boolean = false
)
