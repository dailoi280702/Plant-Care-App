package com.example.plantcare.domain.use_case.authentication

data class LoginSignupUsecases (
  val loginWithEmailAndPassword: LoginWithEmailAndPassword,
  val loginWithGoogle: LoginWithGoogle,
  val loginWithFacebook: LoginWithFacebook,
  val loginWithTwitter: LoginWithTwitter,
  val signupWithEmailAndPassword: SignupWithEmailAndPassword,
  val sendRecoveryEmail: SendRecoveryEmail,
  val logout: Logout,
  val isUserLogedin: IsUserLogedin
)