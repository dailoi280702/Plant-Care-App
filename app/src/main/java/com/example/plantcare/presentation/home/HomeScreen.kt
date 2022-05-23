package com.example.plantcare.presentation.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.plantcare.presentation.main.MainViewModel
import com.example.plantcare.presentation.recently_added_plant.RecentlyAddedPlant

@Composable
fun HomeScreen(
  navController: NavController,
  mainViewModel: MainViewModel
) {

  Column(
    modifier = Modifier
      .fillMaxSize()
      .verticalScroll(rememberScrollState())
  ) {
    RecentlyAddedPlant(navController = navController)
    Text("hldsafjlk")
    Spacer(modifier = Modifier.height(80.dp))
  }
}