package com.example.karaokedemo.data.repository

import com.example.karaokedemo.data.api.Api
import com.example.karaokedemo.data.api.dto.VideoDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception

class VideoRepository {

    private val api: Api = Api.create()

    suspend fun getVideos(): RepositoryResponse {
        return withContext(Dispatchers.IO) {
            try {
                val videos: List<VideoDto> = api.getVideos().await()
                RepositoryResponse.Success(videos)
            } catch (e: Exception) {
                RepositoryResponse.Error
            }
        }
    }

}