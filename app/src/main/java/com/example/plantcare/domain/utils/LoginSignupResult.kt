package com.example.plantcare.domain.utils

sealed class LoginSignupResult(val message: String) {
  object LoginSuccessfully: LoginSignupResult(message = "Log in successfully")
  object SignupSuccessfully: LoginSignupResult(message = "Sign up successfully")
  data class LoginFailed(val mess: String): LoginSignupResult(message = mess)
  data class SignupFailed(val mess: String): LoginSignupResult(message = mess)
}