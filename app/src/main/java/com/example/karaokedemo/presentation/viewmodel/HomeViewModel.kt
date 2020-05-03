package com.example.karaokedemo.presentation.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.karaokedemo.R
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

class HomeViewModel(private val context: Context,
                    private val videoRepository: VideoRepository, private val userRepository: UserRepository)
    : ViewModel() {

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    val user: LiveData<User> = Transformations.map(userRepository.user()) {
        it?.toModel()
    }

    private val _videos = MutableLiveData<List<Video>>()
    val videos: LiveData<List<Video>>
        get() = _videos

    private val _isLoadingVideos = MutableLiveData<Boolean>()
    val isLoadingVideos: LiveData<Boolean>
        get() = _isLoadingVideos

    private val _showError = MutableLiveData<String>()
    val showError: LiveData<String>
        get() = _showError

    init {
        getVideos()
    }

    private fun getVideos() {
        _isLoadingVideos.value = true
        uiScope.launch {
            val videosResponse = videoRepository.getVideos()
            _isLoadingVideos.value = false
            when (videosResponse) {
                RepositoryResponse.Error ->
                    _showError.value = context.getString(R.string.msg_general_connection_error)
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