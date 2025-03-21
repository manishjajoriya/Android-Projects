package com.example.mywishlistapp

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mywishlistapp.data.Wish
import com.example.mywishlistapp.data.WishRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class WishViewModel(private val wishRepo: WishRepo = Graph.wishRepo) : ViewModel() {

  var wishTitleState by mutableStateOf("")
  var wishDescriptionState by mutableStateOf("")

  fun onWishTitleChange(newString: String) {
    wishTitleState = newString
  }

  fun onWishDescriptionChange(newString: String) {
    wishDescriptionState = newString
  }

  lateinit var getAllWish: Flow<List<Wish>>

  init {
    viewModelScope.launch { getAllWish = wishRepo.getAllWishes() }
  }

  fun addWish(wish: Wish) {
    viewModelScope.launch(Dispatchers.IO) {
      wishRepo.addWish(wish = wish)
      withContext(Dispatchers.Main) {
        wishTitleState = ""
        wishDescriptionState = ""
      }
    }
  }

  fun getWish(id: Long) {
    viewModelScope.launch { viewModelScope.launch(Dispatchers.IO) { wishRepo.getWish(id) } }
  }

  fun updateWish(wish: Wish) {
    viewModelScope.launch(Dispatchers.IO) { wishRepo.updateWish(wish = wish) }
  }

  fun deleteWish(wish: Wish) {
    viewModelScope.launch(Dispatchers.IO) { wishRepo.deleteWish(wish = wish) }
  }

  fun getWishById(id: Long): Flow<Wish> {
    return wishRepo.getWish(id)
  }
}
