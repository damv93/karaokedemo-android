package com.example.karaokedemo.data.api.dto

import com.google.gson.annotations.SerializedName

data class UserProfileDto(
    @SerializedName("name")
    val name: String,
    @SerializedName("user_name")
    val userName: String,
    @SerializedName("img")
    val imageUrl: String
)