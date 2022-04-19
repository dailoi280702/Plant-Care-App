package com.example.plantcare.domain.use_case.authentication

import com.example.plantcare.domain.repository.AuthenticationRepository

class IsUserLogedin(
  private val repository: AuthenticationRepository
) {
  operator fun invoke(): Boolean {
    return repository.isUserLogedin()
  }
}