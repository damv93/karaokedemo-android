package com.example.karaokedemo.data.repository

import androidx.lifecycle.LiveData
import com.example.karaokedemo.data.db.dao.UserDao
import com.example.karaokedemo.data.db.entity.UserEntity

class UserRepository(private val userDao: UserDao) {

    fun getUser(): LiveData<UserEntity> = userDao.getUser()

}