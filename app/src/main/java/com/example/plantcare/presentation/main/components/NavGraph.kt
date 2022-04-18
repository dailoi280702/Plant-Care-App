package com.example.plantcare.presentation.main.components

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.plantcare.presentation.home.HomeScreen
import com.example.plantcare.presentation.main.MainViewModel
import com.example.plantcare.presentation.plants.PlantsScreen
import com.example.plantcare.presentation.settings.SettingsScreen
import com.example.plantcare.presentation.signIn_singUp.LoginSignupScreen
import com.example.plantcare.presentation.tasks.TasksScreen
import com.example.plantcare.presentation.utils.Screens

@Composable
fun NavGraph(
  navController: NavHostController,
  mainViewModel: MainViewModel
) {
  NavHost(navController = navController, startDestination = Screens.LoginSignupScreen.route) {
    composable(Screens.MainScreens.Home.route) {
      HomeScreen(navController = navController, mainViewModel = mainViewModel)
    }
    composable(Screens.MainScreens.Plants.route) {
      PlantsScreen(navController = navController, mainViewModel = mainViewModel)
    }
    composable(Screens.MainScreens.Tasks.route) {
      TasksScreen(navController = navController, mainViewModel = mainViewModel)
    }
    composable(Screens.MainScreens.Settings.route) {
      SettingsScreen(navController = navController, mainViewModel = mainViewModel)
    }
    composable(Screens.LoginSignupScreen.route) {
      LoginSignupScreen(navController = navController, mainViewModel = mainViewModel)
    }
  }
}
