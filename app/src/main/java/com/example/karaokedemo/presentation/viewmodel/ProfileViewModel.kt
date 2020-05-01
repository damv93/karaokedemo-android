package com.example.karaokedemo.presentation.viewmodel

import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.karaokedemo.data.repository.UserRepository

class ProfileViewModel(private val userRepository: UserRepository) : ViewModel() {

    private val userEntity = userRepository.getUser()

}