package com.manishjajoriya.myrecipeapp.view

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.manishjajoriya.myrecipeapp.Screen
import com.manishjajoriya.myrecipeapp.model.Category
import com.manishjajoriya.myrecipeapp.viewModel.MainViewModel

@Composable
fun RecipeApp(navController: NavHostController, innerPaddingValues: PaddingValues){
    val recipeViewModel : MainViewModel = viewModel()
    val viewstate by recipeViewModel.categoriesState

    NavHost(navController = navController, startDestination = Screen.RecipeScreen.route ){
        composable(route = Screen.RecipeScreen.route) {
            RecipeScreen(viewState = viewstate, innerPaddingValues = innerPaddingValues){ category ->
                navController.currentBackStackEntry?.savedStateHandle?.set("cat", category)
                navController.navigate(Screen.DetailScreen.route)
            }
        }

        composable(route = Screen.DetailScreen.route){
            val category = navController.previousBackStackEntry?.savedStateHandle?.get<Category>("cat") ?: Category("", "","", "")
            CategoryDetailScreen(category = category, innerPaddingValues)
        }
    }
}

