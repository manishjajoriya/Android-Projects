package com.example.mywishlistapp

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.mywishlistapp.data.Wish
import kotlinx.coroutines.launch

@Composable
fun AddEditDetailView(id: Long, viewModel: WishViewModel, navController: NavController) {

  val snackMessage = remember { mutableStateOf("") }

  val scope = rememberCoroutineScope()
  val snackbarHostState = remember { SnackbarHostState() }

  if (id != 0L) {
    val wish = viewModel.getWishById(id).collectAsState(initial = Wish(0L, "", ""))
    viewModel.wishTitleState = wish.value.title
    viewModel.wishDescriptionState = wish.value.description
  }

  Scaffold(
    snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
    topBar = {
      AppBarView(
        title =
          if (id != 0L) stringResource(R.string.update_wish) else stringResource(R.string.add_wish)
      ) {
        navController.navigateUp()
      }
    },
  ) { innerPadding ->
    Column(
      modifier = Modifier.padding(innerPadding),
      horizontalAlignment = Alignment.CenterHorizontally,
    ) {
      Spacer(modifier = Modifier.height(10.dp))
      WishTextField("Title", viewModel.wishTitleState) { viewModel.onWishTitleChange(it) }
      Spacer(modifier = Modifier.height(10.dp))
      WishTextField("Description", viewModel.wishDescriptionState) {
        viewModel.onWishDescriptionChange(it)
      }
      Spacer(modifier = Modifier.height(20.dp))
      Button(
        onClick = {
          if (
            viewModel.wishTitleState.isNotEmpty() && viewModel.wishDescriptionState.isNotEmpty()
          ) {
            if (id != 0L) {
              viewModel.updateWish(
                Wish(
                  id = id,
                  title = viewModel.wishTitleState.trim(),
                  description = viewModel.wishDescriptionState,
                )
              )
              snackMessage.value = "Wish has been updated."
            } else {

              viewModel.addWish(
                Wish(
                  title = viewModel.wishTitleState.trim(),
                  description = viewModel.wishDescriptionState.trim(),
                )
              )
              snackMessage.value = "Wish has been created."
            }
          } else {
            snackMessage.value = "Enter fields to create a wish"
          }

          scope.launch {
            snackbarHostState.showSnackbar(snackMessage.value)
            navController.navigateUp()
          }
        }
      ) {
        Text(
          text =
            if (id != 0L) stringResource(R.string.update_wish)
            else stringResource(R.string.add_wish),
          style = TextStyle(fontSize = 18.sp),
        )
      }
    }
  }
}

@Composable
fun WishTextField(label: String, value: String, onValueChange: (String) -> Unit) {
  OutlinedTextField(
    value = value,
    label = { Text(label) },
    onValueChange = onValueChange,
    modifier = Modifier.fillMaxWidth().padding(horizontal = 4.dp),
    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
    colors = TextFieldDefaults.colors(focusedTextColor = Color.Black),
  )
}
