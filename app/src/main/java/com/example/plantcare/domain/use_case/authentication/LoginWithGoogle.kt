package com.example.plantcare.domain.use_case.authentication

import com.example.plantcare.domain.repository.AuthenticationRepository
import com.google.firebase.auth.AuthCredential

class LoginWithGoogle(
  private val repository: AuthenticationRepository
) {
  suspend operator fun invoke(credential: AuthCredential) = repository.loginWithGoogle(credential)
}