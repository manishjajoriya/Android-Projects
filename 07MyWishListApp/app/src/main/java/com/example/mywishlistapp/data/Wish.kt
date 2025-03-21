package com.example.mywishlistapp.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "wish-table")
data class Wish(
  @PrimaryKey(autoGenerate = true) val id: Long = 0L,
  @ColumnInfo(name = "wish-title") val title: String = "",
  @ColumnInfo(name = "wish-desc") val description: String = "",
)

object DummyWish {
  val wishList =
    listOf(
      Wish(title = "Become happy", description = "Be happy and make everyone happy around me."),
      Wish(title = "Trip to EU", description = "2 week trip to any EU country with parents."),
    )
}
