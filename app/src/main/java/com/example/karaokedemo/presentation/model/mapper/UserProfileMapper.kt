package com.example.karaokedemo.presentation.model.mapper

import com.example.karaokedemo.data.api.dto.UserProfileDto
import com.example.karaokedemo.presentation.model.UserProfile

fun UserProfileDto.toModel(): UserProfile =
    UserProfile(
        name = name,
        userName = userName,
        imageUrl = imageUrl
    )