package com.example.plantcare.presentation.login_signup.utils

import androidx.compose.runtime.Composable

sealed class LoginSignupTabItem(val tabTitle: String, val screen: @Composable () -> Unit) {
  data class Login(val login: @Composable () -> Unit): LoginSignupTabItem(tabTitle = "Log in", screen = login)
  data class Signup(val signup: @Composable () -> Unit): LoginSignupTabItem(tabTitle = "Sign up", screen = signup)
}