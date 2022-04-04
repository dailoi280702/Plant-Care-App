package com.example.plantcare.presentation.signIn_singUp.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.plantcare.R
import com.example.plantcare.presentation.signIn_singUp.components.LoginTab

sealed class LoginSignupTabItem(val tabTitle: String, val screen: @Composable () -> Unit) {
  data class Login(val login: @Composable () -> Unit): LoginSignupTabItem(tabTitle = "Log in", screen = login)
  data class Signup(val signup: @Composable () -> Unit): LoginSignupTabItem(tabTitle = "Sign up", screen = signup)
}