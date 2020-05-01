package com.example.karaokedemo.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.karaokedemo.data.db.entity.UserEntity

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsert(userEntity: UserEntity)

    @Query("SELECT * FROM user LIMIT 1")
    fun getUser(): LiveData<UserEntity>

}