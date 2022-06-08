package com.example.plantcare.domain.use_case.authentication

import com.example.plantcare.domain.repository.AuthenticationRepository

class SendRecoveryEmail(
  private val repository: AuthenticationRepository
) {
  suspend operator fun invoke(email: String) = repository.sendRecoveryEmail(email)
}
