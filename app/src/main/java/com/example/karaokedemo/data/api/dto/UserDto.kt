package com.example.karaokedemo.data.api.dto

import com.google.gson.annotations.SerializedName

data class UserDto(
    @SerializedName("name")
    val name: String,
    @SerializedName("user_name")
    val username: String,
    @SerializedName("img")
    val imageUrl: String
)