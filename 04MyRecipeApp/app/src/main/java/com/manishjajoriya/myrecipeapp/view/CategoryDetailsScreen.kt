package com.manishjajoriya.myrecipeapp.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.manishjajoriya.myrecipeapp.model.Category


@Composable
fun CategoryDetailScreen(category: Category, innerPaddingValues: PaddingValues) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPaddingValues),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(category.strCategory, textAlign = TextAlign.Center)
        Image(
            painter = rememberAsyncImagePainter(category.strCategoryThumb),
            contentDescription = "Image of ${category.strCategory}",
            modifier = Modifier
                .wrapContentSize()
                .aspectRatio(1f)
        )

        Text(
            text = category.strCategoryDescription,
            textAlign = TextAlign.Justify,
            modifier = Modifier
                .verticalScroll(
                    rememberScrollState()
                )
                .padding(horizontal = 16.dp)
        )
    }
}