package com.example.karaokedemo.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class UserEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,
    @ColumnInfo(name = "name")
    var name: String,
    @ColumnInfo(name = "username")
    var username: String,
    @ColumnInfo(name = "biography")
    var biography: String? = null,
    @ColumnInfo(name = "followers")
    var followers: Int = 0,
    @ColumnInfo(name = "followed")
    var followed: Int = 0,
    @ColumnInfo(name = "views")
    var views: Int = 0,
    @ColumnInfo(name = "profile_picture_uri")
    var profilePictureUri: String? = null
)