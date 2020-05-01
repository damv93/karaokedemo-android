package com.example.karaokedemo.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.karaokedemo.data.db.dao.UserDao
import com.example.karaokedemo.data.db.entity.UserEntity

@Database(entities = [UserEntity::class], version = 1, exportSchema = false)
abstract class KaraokeDatabase : RoomDatabase() {

    abstract val userDao: UserDao

    companion object {

        private const val DATABASE_NAME = "karaoke_database"

        @Volatile
        private var INSTANCE: KaraokeDatabase? = null

        fun getInstance(context: Context): KaraokeDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        KaraokeDatabase::class.java,
                        DATABASE_NAME)
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }

                return instance
            }
        }

    }

}