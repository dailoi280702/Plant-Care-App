package com.example.plantcare.domain.use_case.logIn_signUp

import com.example.plantcare.domain.utils.LoginSignupArgumentException
import com.google.firebase.auth.FirebaseAuth

class SignInWithEmailAndPassword(
  private val auth: FirebaseAuth
) {

  @Throws(LoginSignupArgumentException::class)
  suspend operator fun invoke(
    email: String,
    password: String
  ) {
    if (email.isBlank() || password.isBlank()) {
      throw LoginSignupArgumentException("please enter both email and password")
    }
//implement
  }
}