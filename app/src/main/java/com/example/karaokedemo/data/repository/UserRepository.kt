package com.example.karaokedemo.data.repository

import androidx.lifecycle.LiveData
import com.example.karaokedemo.data.db.dao.UserDao
import com.example.karaokedemo.data.db.entity.UserEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserRepository(private val userDao: UserDao) {

    fun user(): LiveData<UserEntity> = userDao.user()

    fun users(): LiveData<List<UserEntity>> = userDao.users()

    suspend fun getUser(): UserEntity? {
        return withContext(Dispatchers.IO) {
            userDao.getUser()
        }
    }

    suspend fun saveUser(userEntity: UserEntity) {
        withContext(Dispatchers.IO) {
            userDao.upsert(userEntity)
        }
    }

}