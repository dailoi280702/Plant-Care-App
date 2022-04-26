package com.example.plantcare.domain.use_case.authentication

import com.example.plantcare.domain.repository.AuthenticationRepository

class LoginWithFacebook(
  private val repository: AuthenticationRepository
) {
  suspend operator fun invoke() {
    repository.loginWithFacebook()
  }
}