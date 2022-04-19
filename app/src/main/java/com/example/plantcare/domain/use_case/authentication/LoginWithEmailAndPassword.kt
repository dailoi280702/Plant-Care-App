package com.example.plantcare.domain.use_case.authentication

import com.example.plantcare.domain.repository.AuthenticationRepository
import com.example.plantcare.domain.utils.LoginSignupArgumentException

class LoginWithEmailAndPassword(
  private val repository: AuthenticationRepository
) {

  @Throws(LoginSignupArgumentException::class)
  suspend operator fun invoke(
    email: String,
    password: String
  ) {
    if (email.isBlank() || password.isBlank()) {
      throw LoginSignupArgumentException("please enter both email and password")
    }
    repository.loginWithEmailAndPassword(email = email, password = password)
  }
}