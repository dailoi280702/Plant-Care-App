package com.example.plantcare.presentation.login

data class EmailPasswordState(
  val email: String = "",
  val password: String = "",
  val confirmPassword: String = ""
)
