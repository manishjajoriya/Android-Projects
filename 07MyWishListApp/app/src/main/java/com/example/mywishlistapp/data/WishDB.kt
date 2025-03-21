package com.example.mywishlistapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Wish::class], version = 1, exportSchema = false)
abstract class WishDB : RoomDatabase() {
  abstract fun wishDao(): WishDao

  companion object {
    @Volatile private var Instance: WishDB? = null

    fun getDB(context: Context): WishDB {
      return Instance
        ?: synchronized(this) {
          Room.databaseBuilder(context, WishDB::class.java, "item_database").build().also {
            Instance = it
          }
        }
    }
  }
}
