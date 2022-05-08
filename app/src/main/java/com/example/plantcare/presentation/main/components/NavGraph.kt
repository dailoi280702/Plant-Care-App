package com.example.plantcare.presentation.main.components

import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.plantcare.presentation.Tasks.TasksScreen
import com.example.plantcare.presentation.add_edit_plant.AddEditPlantScreen
import com.example.plantcare.presentation.home.HomeScreen
import com.example.plantcare.presentation.login.AuthenticationViewModel
import com.example.plantcare.presentation.login.LoginSignupScreen
import com.example.plantcare.presentation.main.MainViewModel
import com.example.plantcare.presentation.main.utils.Screens
import com.example.plantcare.presentation.plants.PlantsScreen
import com.example.plantcare.presentation.settings.SettingsScreen

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
    composable(
      route = Screens.AddPlantScreen.route + "?plantId={plantId}",
      arguments = listOf(
        navArgument(
          name = "plantId"
        ) {
          type = NavType.StringType
          defaultValue = ""
        }
      )
    ) {
      AddEditPlantScreen(
        navController = navController,
        mainViewModel = mainViewModel
      )
    }
  }
}
