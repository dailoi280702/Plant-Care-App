package com.example.plantcare.domain.use_case.logIn_signUp

data class LoginSignupUsecases (
  val loginWithEmailAndPassword: LoginWithEmailAndPassword,
  val loginWithGoogle: LoginWithGoogle,
  val loginWithFacebook: LoginWithFacebook,
  val loginWithTwitter: LoginWithTwitter,
  val signupWithEmailAndPassword: SignupWithEmailAndPassword,
  val logout: Logout
)