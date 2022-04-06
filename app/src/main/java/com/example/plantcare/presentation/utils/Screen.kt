package com.example.plantcare.presentation.utils

sealed class Screen(val route: String) {
  object LoginSignupScreen: Screen("login_screen")
  object MainScreen: Screen("main_screen")
}