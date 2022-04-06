package com.example.plantcare.presentation.signIn_singUp

sealed class LoginSignupEvent {
  data class EnterLoginEmail(val value: String): LoginSignupEvent()
  data class EnterLoginPassword(val value: String): LoginSignupEvent()
  data class EnterSignupEmail(val value: String): LoginSignupEvent()
  data class EnterSignupPassword(val value: String): LoginSignupEvent()
  object LoginWithEmailAndPassword: LoginSignupEvent()
  object SignupWithEmailAndPasswrd: LoginSignupEvent()
  object LoginWithFaceBook: LoginSignupEvent()
  object LoginWithGoogle: LoginSignupEvent()
  object LoginWithTwitter: LoginSignupEvent()
}


sealed class LoginSignupUIEvent {
  data class ShowText(val message: String): LoginSignupUIEvent()
  object NavigateToMainScreen: LoginSignupUIEvent()
}
