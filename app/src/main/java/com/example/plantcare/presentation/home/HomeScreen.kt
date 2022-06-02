package com.example.plantcare.presentation.home

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.plantcare.presentation.recently_added_plant.RecentlyAddedPlant
import com.example.plantcare.presentation.todo_dashboard.components.TodoDashBoard

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
  navController: NavController,
  bottomBar: @Composable () -> Unit
) {
  
  Scaffold(
    bottomBar = bottomBar
  ) {
    Column(
      modifier = Modifier
        .fillMaxSize()
        .verticalScroll(rememberScrollState())
    ) {
      RecentlyAddedPlant(navController = navController)
      TodoDashBoard(navController = navController)
      Spacer(modifier = Modifier.height(80.dp))
    }
  }
}