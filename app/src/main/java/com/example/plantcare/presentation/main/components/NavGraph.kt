package com.example.plantcare.presentation.main.components

import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.plantcare.presentation.home.HomeScreen
import com.example.plantcare.presentation.login_signup.AuthenticationViewModel
import com.example.plantcare.presentation.login_signup.LoginSignupScreen
import com.example.plantcare.presentation.main.MainViewModel
import com.example.plantcare.presentation.plants.PlantsScreen
import com.example.plantcare.presentation.settings.SettingsScreen
import com.example.plantcare.presentation.tasks.TasksScreen
import com.example.plantcare.presentation.utils.Screens

@Composable
fun NavGraph(
  navController: NavHostController,
  scaffoldState: ScaffoldState,
  mainViewModel: MainViewModel,
  authenticationViewModel: AuthenticationViewModel
) {
  val startDest =
    if (authenticationViewModel.isUserLogedin()) Screens.MainScreens.Home else Screens.LoginSignupScreen
  mainViewModel.setCurrentScreen(startDest)
  NavHost(navController = navController, startDestination = startDest.route) {
    composable(Screens.MainScreens.Home.route) {
      HomeScreen(
        navController = navController,
        mainViewModel = mainViewModel
      )
    }
    composable(Screens.MainScreens.Plants.route) {
      PlantsScreen(
        navController = navController,
        scaffoldState = scaffoldState,
        mainViewModel = mainViewModel
      )
    }
    composable(Screens.MainScreens.Tasks.route) {
      TasksScreen(
        navController = navController,
        mainViewModel = mainViewModel
      )
    }
    composable(Screens.MainScreens.Settings.route) {
      SettingsScreen(
        navController = navController,
        mainViewModel = mainViewModel
      )
    }
    composable(Screens.LoginSignupScreen.route) {
      LoginSignupScreen(
        navController = navController,
        mainViewModel = mainViewModel
      )
    }
  }
}