package com.example.plantcare.presentation.utils

import com.example.plantcare.R

//sealed class Screen(val route: String) {
//  object LoginSignupScreen : Screen("login_screen")
//  object MainScreen : Screen("main_screen")
//}

sealed class Screens(val route: String) {
  object LoginSignupScreen : Screens(route = "login_screen")
  object AddEditPictureScreen : Screens(route = "add_edit_picture")
  object AddPlantScreen : Screens(route = "add_plant")
  object AddTaskScreen : Screens(route = "add_task")
  sealed class MainScreens(route: String, val title: String, val icon: Int, val outline_icon: Int) :
    Screens(route) {
    object Home : MainScreens(
      route = "home",
      title = "Home",
      icon = R.drawable.ic_home_simple,
      outline_icon = R.drawable.ic_home_outline_simple
    )

    object Plants : MainScreens(
      route = "plants",
      title = "Plants",
      icon = R.drawable.ic_leaf,
      outline_icon = R.drawable.ic_leaf_outline
    )

    object Tasks : MainScreens(
      route = "tasks",
      title = "Todo",
      icon = R.drawable.ic_todo,
      outline_icon = R.drawable.ic_todo_outline
    )

    object Settings : MainScreens(
        route = "settings",
        title = "Settings",
        icon = R.drawable.ic_baseline_settings_24,
        outline_icon = R.drawable.ic_outline_settings_24
      )
  }
}