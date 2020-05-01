package com.example.karaokedemo.data.api

import com.example.karaokedemo.data.api.dto.VideoDto
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface Api {
    @GET("5e669952310000d2fc23a20e")
    fun getVideos(): Deferred<List<VideoDto>>

    companion object {
        private const val BASE_URL = "http://www.mocky.io/v2/"
        fun create(): Api =
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .build()
                .create(Api::class.java)
    }
}
