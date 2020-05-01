package com.example.karaokedemo.presentation.model.mapper

import com.example.karaokedemo.data.api.dto.VideoDto
import com.example.karaokedemo.presentation.model.Video

fun VideoDto.toModel(): Video =
    Video(
        description = description,
        url = url,
        previewImageUrl = previewImageUrl,
        user = user.toModel()
    )

fun List<VideoDto>.toModel(): List<Video> = map { dto -> dto.toModel() }