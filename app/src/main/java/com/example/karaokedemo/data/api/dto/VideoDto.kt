package com.example.karaokedemo.data.api.dto

import com.google.gson.annotations.SerializedName

data class VideoDto(
    @SerializedName("description")
    val description: String,
    @SerializedName("record_video")
    val url: String,
    @SerializedName("preview_img")
    val previewImageUrl: String,
    @SerializedName("profile")
    val user: UserProfileDto
)