package com.example.plantcare.presentation

import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.plantcare.presentation.main.MainViewModel
import com.example.plantcare.presentation.main.components.BottomNav
import com.example.plantcare.presentation.main.components.NavGraph
import com.example.plantcare.presentation.utils.Screens

@Composable
fun AppScaffold() {
  val viewModel: MainViewModel = viewModel()
  val scaffoldState = rememberScaffoldState()
  val navController = rememberNavController()
//  val currentRoute = remember {
//    mutableStateOf(
//      navController.currentBackStackEntry?.destination?.route
//    )
//  }
//  val screensRoute = listOf( Screens.MainScreens.Home.route,
//    Screens.MainScreens.Plants.route,
//    Screens.MainScreens.Tasks.route,
//    Screens.MainScreens.Settings.route,
//  )

  val bottomBar: @Composable () -> Unit = {
    if (viewModel.currentScreen.value is Screens.MainScreens) {
      BottomNav(navController = navController)
    }
  }

  Scaffold(
    scaffoldState = scaffoldState,
    bottomBar = {
      bottomBar()
    }
  ) {
    NavGraph(navController = navController, mainViewModel = viewModel)
  }
}