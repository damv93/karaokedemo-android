package com.example.karaokedemo.presentation.model.mapper

import com.example.karaokedemo.data.api.dto.UserDto
import com.example.karaokedemo.data.db.entity.UserEntity
import com.example.karaokedemo.presentation.model.User

fun UserDto.toModel(): User =
    User(
        name = name,
        username = username,
        imageUri = imageUrl
    )

fun UserEntity.toModel(): User =
    User(
        id = id,
        name = name,
        username = username,
        biography = biography,
        imageUri = profilePictureUri
    )

fun User.toEntity(): UserEntity =
    UserEntity(
        id = id,
        name = name,
        username = username,
        biography = biography,
        profilePictureUri = imageUri
    )