package com.example.plantcare.presentation.plants

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.plantcare.presentation.main.MainViewModel

@Composable
fun PlantsScreen(
  navController: NavController,
  mainViewModel: MainViewModel
) {
  Box(modifier = Modifier.fillMaxSize()) {
    Text(text = "Plants Screen")
  }
}