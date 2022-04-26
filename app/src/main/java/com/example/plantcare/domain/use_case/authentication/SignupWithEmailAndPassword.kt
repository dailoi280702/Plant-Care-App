package com.example.plantcare.domain.use_case.authentication

import com.example.plantcare.domain.repository.AuthenticationRepository
import com.example.plantcare.domain.utils.LoginSignupArgumentException

class SignupWithEmailAndPassword(
  private val repository: AuthenticationRepository
) {
  @Throws(LoginSignupArgumentException::class)
  suspend operator fun invoke(
    email: String,
    password: String,
    confirmPassword: String
  ) {
    if (email.isBlank() || password.isBlank()) {
      throw LoginSignupArgumentException("please enter both email and password")
    }
    if (confirmPassword.isBlank()) {
      throw LoginSignupArgumentException("please confirm your password")
    }
    if (password != confirmPassword) {
      throw  LoginSignupArgumentException("those password doesn't match")
    }
    repository.signupWithEmailAndPassword(email = email, password = password)
  }
}