package com.example.plantcare.presentation.main.components

import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.plantcare.presentation.todos.TasksScreen
import com.example.plantcare.presentation.plant_detail.AddEditPlantScreen
import com.example.plantcare.presentation.home.HomeScreen
import com.example.plantcare.presentation.login.AuthenticationViewModel
import com.example.plantcare.presentation.login.LoginSignupScreen
import com.example.plantcare.presentation.main.utils.Screens
import com.example.plantcare.presentation.plants.PlantsScreen
import com.example.plantcare.presentation.settings.SettingsScreen

@Composable
fun NavGraph(
  navController: NavHostController,
  scaffoldState: ScaffoldState,
  authenticationViewModel: AuthenticationViewModel,
) {
  
  val bottomBar: @Composable () -> Unit = { BottomNav(navController = navController) }
  val startDest =
    if (authenticationViewModel.isUserLogedin()) Screens.MainScreens.Home else Screens.LoginSignupScreen
  
  NavHost(navController = navController, startDestination = startDest.route) {
    composable(Screens.MainScreens.Home.route) {
      HomeScreen(
        navController = navController,
        bottomBar = bottomBar
      )
    }
    composable(Screens.MainScreens.Plants.route) {
      PlantsScreen(
        navController = navController,
        scaffoldState = scaffoldState,
        bottomBar = bottomBar
      )
    }
    composable(Screens.MainScreens.Tasks.route) {
      TasksScreen(
        navController = navController,
        bottomBar = bottomBar
      )
    }
    composable(Screens.MainScreens.Settings.route) {
      SettingsScreen(
        navController = navController,
        bottomBar = bottomBar
      )
    }
    composable(Screens.LoginSignupScreen.route) {
      LoginSignupScreen(
        navController = navController,
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
        navController = navController
      )
    }
  }
}
