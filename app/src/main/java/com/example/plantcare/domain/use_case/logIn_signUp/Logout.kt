package com.example.plantcare.domain.use_case.logIn_signUp

import com.example.plantcare.domain.repository.LoginSignupRepository

class Logout(
  private val repository: LoginSignupRepository
) {
  suspend operator fun invoke() {
    repository.logout()
  }
}