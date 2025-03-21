package com.example.mywishlistapp

import android.content.Context
import com.example.mywishlistapp.data.WishDB
import com.example.mywishlistapp.data.WishRepo

object Graph {
  lateinit var db: WishDB

  val wishRepo by lazy { WishRepo(wishDao = db.wishDao()) }

  fun provide(context: Context) {
    db = WishDB.getDB(context)
  }
}
