package com.example.plantcare.presentation

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.plantcare.R
import com.example.plantcare.presentation.login_signup.AuthenticationViewModel
import com.example.plantcare.presentation.main.MainViewModel
import com.example.plantcare.presentation.main.components.BottomNav
import com.example.plantcare.presentation.main.components.NavGraph
import com.example.plantcare.presentation.utils.Screens
import com.example.plantcare.ui.theme.utils.customColors

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
    scaffoldState = scaffoldState,
    bottomBar = {
      if (bottomNavRoutes.contains(navBackStackEntry?.destination?.route))
        BottomNav(navController = navController)
    },
//    floatingActionButton = viewModel.floatingActionButton.value
    floatingActionButton = {
      if (!floatingActionButtonRoutes.contains(navBackStackEntry?.destination?.route))
        FloatingActionButton(
          onClick = viewModel.fbaState.value.onClick,
          shape = RoundedCornerShape(16.dp),
          backgroundColor = MaterialTheme.customColors.primaryContainer
        ) {
          Icon(
            painter = painterResource(id = if (viewModel.fbaState.value.icon != -1) viewModel.fbaState.value.icon else R.drawable.ic_edit_outline),
            contentDescription = viewModel.fbaState.value.contentDescription,
            tint = MaterialTheme.customColors.onPrimaryContainer,
            modifier = Modifier.size(24.dp)
          )
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