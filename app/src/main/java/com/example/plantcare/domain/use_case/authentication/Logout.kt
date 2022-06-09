package com.example.plantcare.domain.use_case.authentication

import android.util.Log
import com.example.plantcare.domain.repository.AuthenticationRepository

class Logout(
  private val repository: AuthenticationRepository
) {
  suspend operator fun invoke() {
    repository.logout()
  }
}