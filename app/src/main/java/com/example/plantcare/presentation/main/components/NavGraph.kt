package com.example.plantcare.presentation.main.components

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.*
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.plantcare.presentation.todos.TasksScreen
import com.example.plantcare.presentation.plant_detail.AddEditPlantScreen
import com.example.plantcare.presentation.home.HomeScreen
import com.example.plantcare.presentation.login.AuthenticationViewModel
import com.example.plantcare.presentation.login.LoginSignupScreen
import com.example.plantcare.presentation.main.utils.Screens
import com.example.plantcare.presentation.plants.PlantsScreen
import com.example.plantcare.presentation.settings.SettingsScreen
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun NavGraph(
  navController: NavHostController = rememberAnimatedNavController(),
  scaffoldState: ScaffoldState,
  authenticationViewModel: AuthenticationViewModel = hiltViewModel(),
) {
  
  val bottomBar: @Composable () -> Unit = { BottomNav(navController = navController) }
  val startDest =
    if (authenticationViewModel.isUserLogedin()) Screens.MainScreens.Home else Screens.LoginSignupScreen
  
  AnimatedNavHost(navController = navController, startDestination = startDest.route) {
    composable(
      Screens.MainScreens.Home.route,
      enterTransition = { fadeIn() },
      exitTransition = { fadeOut() }
    ) {
      HomeScreen(
        navController = navController,
        bottomBar = bottomBar
      )
    }
    composable(Screens.MainScreens.Plants.route,
      enterTransition = { fadeIn() },
      exitTransition = { fadeOut() }
    ) {
      PlantsScreen(
        navController = navController,
        scaffoldState = scaffoldState,
        bottomBar = bottomBar
      )
    }
    composable(Screens.MainScreens.Tasks.route,
      enterTransition = { fadeIn() },
      exitTransition = { fadeOut() }
    ) {
      TasksScreen(
        navController = navController,
        bottomBar = bottomBar
      )
    }
    composable(Screens.MainScreens.Settings.route,
      enterTransition = { fadeIn() },
      exitTransition = { fadeOut() }
    ) {
      SettingsScreen(
        navController = navController,
        bottomBar = bottomBar
      )
    }
    composable(Screens.LoginSignupScreen.route,
      enterTransition = { fadeIn() },
      exitTransition = { fadeOut() }
    ) {
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
      ),
      enterTransition = {
        slideIntoContainer(
          AnimatedContentScope.SlideDirection.Left,
          animationSpec = tween(500)
        )
      },
      exitTransition = {
        slideOutOfContainer(
          AnimatedContentScope.SlideDirection.Left,
          animationSpec = tween(500)
        )
      },
      popEnterTransition = {
        slideIntoContainer(
          AnimatedContentScope.SlideDirection.Right,
          animationSpec = tween(500)
        )
      },
      popExitTransition = {
        slideOutOfContainer(
          AnimatedContentScope.SlideDirection.Right,
          animationSpec = tween(500)
        )
      },
    ) {
      AddEditPlantScreen(
        navController = navController
      )
    }
  }
}
