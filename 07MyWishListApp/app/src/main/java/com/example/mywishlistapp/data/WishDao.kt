package com.example.mywishlistapp.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
abstract class WishDao {

  @Upsert abstract suspend fun addWish(wishEntity: Wish)

  @Query("SELECT * FROM `wish-table`") abstract fun getAllWishes(): Flow<List<Wish>>

  @Update abstract suspend fun updateWish(wishEntity: Wish)

  @Delete abstract suspend fun deleteWish(wishEntity: Wish)

  @Query("SELECT * FROM `wish-table` WHERE id=:id") abstract fun getWishById(id: Long): Flow<Wish>
}
