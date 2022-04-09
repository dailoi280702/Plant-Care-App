package com.example.plantcare.domain.use_case.logIn_signUp

import com.example.plantcare.domain.repository.LoginSignupRepository
import com.example.plantcare.domain.utils.LoginSignupArgumentException

class LoginWithEmailAndPassword(
  private val repository: LoginSignupRepository
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