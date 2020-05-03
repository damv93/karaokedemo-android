package com.example.karaokedemo.presentation.model

data class User(
    val id: Long = 0,
    val name: String,
    val username: String,
    val biography: String? = null,
    val imageUri: String? = null
) {
    val fullUsername: String
        get() = "@$username"
}