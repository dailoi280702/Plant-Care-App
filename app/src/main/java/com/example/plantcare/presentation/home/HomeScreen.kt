package com.example.plantcare.presentation.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.plantcare.presentation.main.MainViewModel

@Composable
fun HomeScreen(
  navController: NavController,
  mainViewModel: MainViewModel
) {
  Box(modifier = Modifier.fillMaxSize()) {
    Text(text = "Home")
    Image(
      painter = rememberAsyncImagePainter(model = "https://images.pexels.com/photos/970089/pexels-photo-970089.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1"),
      contentDescription = null,
      modifier = Modifier.fillMaxSize(),
      contentScale = ContentScale.Crop
    )
  }
}