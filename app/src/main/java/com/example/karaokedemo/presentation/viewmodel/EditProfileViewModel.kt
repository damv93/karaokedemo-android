package com.example.karaokedemo.presentation.viewmodel

import android.content.Context
import android.text.TextUtils
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.karaokedemo.R
import com.example.karaokedemo.data.repository.UserRepository
import com.example.karaokedemo.presentation.model.User
import com.example.karaokedemo.presentation.model.mapper.toEntity
import com.example.karaokedemo.presentation.model.mapper.toModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class EditProfileViewModel(private val context: Context, private val userRepository: UserRepository)
    : ViewModel() {

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private var userId = 0L
    val name = MutableLiveData<String>()
    val username = MutableLiveData<String>()
    val biography = MutableLiveData<String>()
    var profileImageUri = MutableLiveData<String>()

    private val _showErrors = MutableLiveData<Boolean>()
    val showErrors: LiveData<Boolean>
        get() = _showErrors

    val nameError: LiveData<String> = Transformations.map(name) {
        getNameError()
    }
    val usernameError: LiveData<String> = Transformations.map(username) {
        getUsernameError()
    }

    private val _onUserSaved = MutableLiveData<Boolean>()
    val onUserSaved: LiveData<Boolean>
        get() = _onUserSaved

    init {
        uiScope.launch {
            val userEntity = userRepository.getUser()
            val user = userEntity?.toModel()
            userId = user?.id ?: 0
            name.value = user?.name
            username.value = user?.username
            biography.value = user?.biography
            profileImageUri.value = user?.imageUri
        }
    }

    fun saveUser() {
        if (!isValidUser()) {
            _showErrors.value = true
            return
        }
        val user = User(
            id = userId,
            name = name.value!!,
            username = username.value!!,
            biography = biography.value,
            imageUri = profileImageUri.value
        )
        uiScope.launch {
            userRepository.saveUser(user.toEntity())
            _onUserSaved.value = true
        }
    }

    fun finishOnUserSaved() {
        _onUserSaved.value = false
    }

    private fun getNameError(): String? {
        return if (TextUtils.isEmpty(name.value))
            context.getString(R.string.msg_field_required_error, "name")
        else null
    }

    private fun getUsernameError(): String? {
        return if (TextUtils.isEmpty(username.value))
            context.getString(R.string.msg_field_required_error, "username")
        else null
    }

    private fun isValidUser(): Boolean =
        getNameError() == null && getUsernameError() == null

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

}