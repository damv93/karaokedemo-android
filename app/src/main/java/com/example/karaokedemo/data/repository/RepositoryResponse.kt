package com.example.karaokedemo.data.repository

sealed class RepositoryResponse {
    object Error : RepositoryResponse()
    data class Success<T>(val data: T) : RepositoryResponse()
}