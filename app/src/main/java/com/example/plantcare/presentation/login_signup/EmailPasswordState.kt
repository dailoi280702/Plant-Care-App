package com.example.plantcare.presentation.login_signup

data class EmailPasswordState(
  val email: String = "",
  val password: String = "",
  val confirmPassword: String = ""
)
