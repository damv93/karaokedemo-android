package com.example.karaokedemo.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.karaokedemo.data.api.dto.VideoDto
import com.example.karaokedemo.data.repository.RepositoryResponse
import com.example.karaokedemo.data.repository.UserRepository
import com.example.karaokedemo.data.repository.VideoRepository
import com.example.karaokedemo.presentation.model.User
import com.example.karaokedemo.presentation.model.Video
import com.example.karaokedemo.presentation.model.mapper.toModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class HomeViewModel(private val videoRepository: VideoRepository, private val userRepository: UserRepository)
    : ViewModel() {

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    val user: LiveData<User> = Transformations.map(userRepository.user()) {
        it?.toModel()
    }

    private val _videos = MutableLiveData<List<Video>>()
    val videos: LiveData<List<Video>>
        get() = _videos

    init {
        getVideos()
    }

    private fun getVideos() {
        uiScope.launch {
            val videosResponse = videoRepository.getVideos()
            when (videosResponse) {
                RepositoryResponse.Error -> {}
                is RepositoryResponse.Success<*> -> {
                    val videoDtos = videosResponse.data as List<VideoDto>
                    _videos.value = videoDtos.toModel()
                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

}