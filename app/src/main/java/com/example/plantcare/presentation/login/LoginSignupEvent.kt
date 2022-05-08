package com.example.plantcare.presentation.login

import com.example.plantcare.presentation.main.utils.Screens

sealed class LoginSignupEvent {
  data class EnterLoginEmail(val value: String): LoginSignupEvent()
  data class EnterLoginPassword(val value: String): LoginSignupEvent()
  data class EnterSignupEmail(val value: String): LoginSignupEvent()
  data class EnterSignupPassword(val value: String): LoginSignupEvent()
  data class EnterSignupConfirmPassword(val value: String): LoginSignupEvent()
  object LoginWithEmailAndPassword: LoginSignupEvent()
  object SignupWithEmailAndPassword: LoginSignupEvent()
  object LoginWithFaceBook: LoginSignupEvent()
  object LoginWithGoogle: LoginSignupEvent()
  object LoginWithTwitter: LoginSignupEvent()
  object SignOut: LoginSignupEvent()
}

sealed class LoginSignupUIEvent {
  data class ShowText(val message: String): LoginSignupUIEvent()
  data class Navigate(val screen: Screens): LoginSignupUIEvent()
}

sealed class EventType {
  object Login: EventType()
  object Signup: EventType()
}
