package com.example.karaokedemo.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class UserEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Long,
    @ColumnInfo(name = "name")
    var name: String,
    @ColumnInfo(name = "username")
    var username: String,
    @ColumnInfo(name = "biography")
    var biography: String,
    @ColumnInfo(name = "followers")
    var followers: Int,
    @ColumnInfo(name = "followed")
    var followed: Int,
    @ColumnInfo(name = "views")
    var views: Int,
    @ColumnInfo(name = "profile_picture_uri")
    var profilePictureUri: String
)