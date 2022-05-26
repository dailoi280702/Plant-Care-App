package com.example.plantcare.presentation

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.size
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.plantcare.R
import com.example.plantcare.presentation.login.AuthenticationViewModel
import com.example.plantcare.presentation.main.MainViewModel
import com.example.plantcare.presentation.main.components.BottomNav
import com.example.plantcare.presentation.main.components.NavGraph
import com.example.plantcare.presentation.main.utils.Screens

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppScaffold(
  viewModel: MainViewModel = viewModel(),
  authenticationViewModel: AuthenticationViewModel = hiltViewModel()
) {
  val scaffoldState = rememberScaffoldState()
  val navController = rememberNavController()
  val navBackStackEntry by navController.currentBackStackEntryAsState()

  val bottomBar: @Composable () -> Unit = {
    if (viewModel.currentScreen.value is Screens.MainScreens) {
      BottomNav(navController = navController)
    }
  }

  val bottomNavRoutes = listOf<String>(
    Screens.MainScreens.Home.route,
    Screens.MainScreens.Plants.route,
    Screens.MainScreens.Tasks.route,
    Screens.MainScreens.Settings.route
  )

  val floatingActionButtonRoutes = listOf<String>(
    Screens.MainScreens.Home.route,
    Screens.MainScreens.Settings.route,
    Screens.LoginSignupScreen.route
  )

  Scaffold(
    bottomBar = {
      if (bottomNavRoutes.contains(navBackStackEntry?.destination?.route))
        BottomNav(navController = navController)
    },
    floatingActionButton = {
      if (!floatingActionButtonRoutes.contains(navBackStackEntry?.destination?.route)) {
        FloatingActionButton(
          onClick = viewModel.fbaState.value.onClick,
          elevation = FloatingActionButtonDefaults.elevation()
        ) {
          Icon(
            painter = painterResource(id = if (viewModel.fbaState.value.icon != -1) viewModel.fbaState.value.icon else R.drawable.ic_edit_outline),
            contentDescription = viewModel.fbaState.value.contentDescription,
            modifier = Modifier
              .size(24.dp)
              .rotate(viewModel.fbaState.value.rotation)
          )
        }
      }
    }
  ) {
    NavGraph(
      navController = navController,
      scaffoldState = scaffoldState,
      mainViewModel = viewModel,
      authenticationViewModel = authenticationViewModel
    )
  }
}