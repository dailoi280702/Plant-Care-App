package com.example.plantcare.presentation

import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.plantcare.presentation.login_signup.AuthenticationViewModel
import com.example.plantcare.presentation.main.MainViewModel
import com.example.plantcare.presentation.main.components.BottomNav
import com.example.plantcare.presentation.main.components.NavGraph
import com.example.plantcare.presentation.utils.Screens

@Composable
fun AppScaffold(
  viewModel: MainViewModel = viewModel(),
  authenticationViewModel: AuthenticationViewModel = hiltViewModel()
) {
  val scaffoldState = rememberScaffoldState()
  val navController = rememberNavController()

  val bottomBar: @Composable () -> Unit = {
    if (viewModel.currentScreen.value is Screens.MainScreens) {
      BottomNav(navController = navController)
    }
  }

  Scaffold(
    scaffoldState = scaffoldState,
    bottomBar = {
      bottomBar()
    },
    floatingActionButton = viewModel.floatingActionButton.value
  ) {
    NavGraph(
      navController = navController,
      scaffoldState = scaffoldState,
      mainViewModel = viewModel,
      authenticationViewModel = authenticationViewModel
    )
  }
}