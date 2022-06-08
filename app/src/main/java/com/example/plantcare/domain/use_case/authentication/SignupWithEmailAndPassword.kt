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
  ) = repository.signupWithEmailAndPassword(email = email, password = password, confirmPassword = confirmPassword)
}