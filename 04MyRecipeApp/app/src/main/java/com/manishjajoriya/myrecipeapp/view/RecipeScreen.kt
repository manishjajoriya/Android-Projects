package com.manishjajoriya.myrecipeapp.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.manishjajoriya.myrecipeapp.model.Category
import com.manishjajoriya.myrecipeapp.viewModel.MainViewModel

@Composable
fun RecipeScreen(innerPaddingValues: PaddingValues,viewState: MainViewModel.RecipeState, navigateToDetail: (Category) -> Unit) {
    Box(modifier = Modifier.fillMaxSize().padding(innerPaddingValues)) {
        when {
            viewState.loading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }

            viewState.error != null -> {
                Text("ERROR OCCURRED", textAlign = TextAlign.Center)
            }

            else -> {
                CategoryScreen(categories = viewState.list, navigateToDetail)
            }
        }
    }
}

@Composable
fun CategoryScreen(categories: List<Category>, navigateToDetail: (Category) -> Unit) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier.fillMaxSize()
    ) {
        items(categories) {
            category ->
            CategoryItem(category = category, navigateToDetail)
        }
    }
}

@Composable
fun CategoryItem(category: Category, navigateToDetail: (Category) -> Unit) {
    Column(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxSize()
            .clickable { navigateToDetail(category) },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = rememberAsyncImagePainter(category.strCategoryThumb),
            contentDescription = "Image of ${category.strCategory}",
            modifier = Modifier.fillMaxSize().aspectRatio(1f)
        )

        Text(
            text = category.strCategory,
            color = MaterialTheme.colorScheme.secondary,
            style = TextStyle(fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(top = 4.dp)
        )
    }
}