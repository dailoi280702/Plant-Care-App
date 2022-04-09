package com.example.plantcare.presentation.signIn_singUp

data class EmailPasswordState(
  val email: String = "",
  val password: String = "",
  val confirmPassword: String = ""
)
