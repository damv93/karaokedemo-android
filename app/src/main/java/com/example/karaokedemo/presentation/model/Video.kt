package com.example.karaokedemo.presentation.model

data class Video(
    val description: String,
    val url: String,
    val previewImageUrl: String,
    val user: User
)