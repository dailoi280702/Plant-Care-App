package com.example.plantcare.presentation.utils

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector

//sealed class Screen(val route: String) {
//  object LoginSignupScreen : Screen("login_screen")
//  object MainScreen : Screen("main_screen")
//}

sealed class Screens(val route: String) {
  object LoginSignupScreen : Screens(route = "login_screen")
  object AddEditPictureScreen : Screens(route = "add_edit_picture")
  object AddPlantScreen : Screens(route = "add_plant")
  object AddTaskScreen : Screens(route = "add_task")
  sealed class MainScreens(route: String, val title: String, val icon: ImageVector) : Screens(route) {
    object Home : MainScreens(route = "home", title = "home icon", icon = Icons.Default.Home)
    object Plants : MainScreens(route = "plants", title = "plant icon", icon = Icons.Default.Favorite)
    object Tasks : MainScreens(route = "tasks", title = "task icon", icon = Icons.Default.Menu)
    object Settings : MainScreens(route = "settings", title = "setting icon", icon = Icons.Default.Settings)
  }
}