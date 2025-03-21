package com.example.mywishlistapp

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.mywishlistapp.data.Wish

@Composable
fun HomeView(navController: NavController, viewModel: WishViewModel) {
  val context = LocalContext.current
  Scaffold(
    topBar = {
      AppBarView(
        title = "WishList",
        onBackNavClicked = { Toast.makeText(context, "Back", Toast.LENGTH_SHORT).show() },
      )
    },
    floatingActionButton = {
      FloatingActionButton(
        contentColor = Color.White,
        containerColor = colorResource(R.color.app_bar_color),
        content = { Icon(imageVector = Icons.Default.Add, contentDescription = "Add Wish") },
        onClick = {
          Toast.makeText(context, "Add", Toast.LENGTH_SHORT).show()
          navController.navigate(Screen.AddScreen.route + "/0L")
        },
        shape = CircleShape,
        modifier = Modifier.padding(bottom = 16.dp, end = 12.dp),
      )
    },
  ) { paddingValues ->
    val wishlist = viewModel.getAllWish.collectAsState(initial = listOf())
    LazyColumn(modifier = Modifier.fillMaxSize().padding(paddingValues)) {
      items(wishlist.value, key = { wish -> wish.id }) { wish ->
        val dismissState =
          rememberSwipeToDismissBoxState(
            confirmValueChange = {
              if (
                it == SwipeToDismissBoxValue.EndToStart || it == SwipeToDismissBoxValue.StartToEnd
              ) {
                viewModel.deleteWish(wish)
              }
              true
            },
            positionalThreshold = { totalDistance -> 0.3f * totalDistance },
          )

        SwipeToDismissBox(
          state = dismissState,
          backgroundContent = {
            Box(
              modifier =
                Modifier.fillMaxSize()
                  .padding(top = 8.dp, start = 8.dp, end = 8.dp)
                  .clip(RoundedCornerShape(12.dp))
                  .background(Color.Red),
              contentAlignment = Alignment.Center,
            ) {
              Row(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
              ) {
                Text("Deleting...", color = Color.White, modifier = Modifier.padding(start = 8.dp))
                Text("Deleting...", color = Color.White, modifier = Modifier.padding(end = 8.dp))
              }
            }
          },
          content = {
            WishItem(wish) {
              Toast.makeText(context, "${wish.id} clicked", Toast.LENGTH_SHORT).show()
              val id = wish.id
              navController.navigate(Screen.AddScreen.route + "/$id")
            }
          },
        )
      }
    }
  }
}

@Composable
fun WishItem(wish: Wish, onClick: () -> Unit) {
  Card(
    modifier =
      Modifier.fillMaxWidth().padding(top = 8.dp, start = 8.dp, end = 8.dp).clickable { onClick() },
    elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
    colors = CardDefaults.cardColors(containerColor = Color.White),
  ) {
    Column(modifier = Modifier.padding(16.dp)) {
      Text(text = wish.title, fontWeight = FontWeight.ExtraBold)
      Text(text = wish.description)
    }
  }
}
