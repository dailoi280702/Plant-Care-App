package com.example.plantcare.presentation.signIn_singUp

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.plantcare.domain.use_case.logIn_signUp.LoginSignupUsecases
import com.example.plantcare.domain.utils.LoginSignupArgumentException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginSignupViewModel @Inject constructor(
  private val loginSignupUsecases: LoginSignupUsecases
) : ViewModel() {

  private val _loginEmailPassword = mutableStateOf(
    EmailPasswordState()
  )
  val loginEmailPassword: State<EmailPasswordState> = _loginEmailPassword

  private val _signupEmailPassword = mutableStateOf(
    EmailPasswordState()
  )
  val signupEmailPassword: State<EmailPasswordState> = _signupEmailPassword

  private val _eventFLow = MutableSharedFlow<LoginSignupUIEvent>()
  val eventFlow = _eventFLow.asSharedFlow()

  private fun resetUI(type: EventType) {
    when (type) {
      is EventType.Login -> {
        _loginEmailPassword.value = loginEmailPassword.value.copy(
          email = "",
          password = ""
        )
      }
      is EventType.Signup -> {
        _signupEmailPassword.value = signupEmailPassword.value.copy(
          email = "",
          password = "",
          confirmPassword = ""
        )
      }
    }
  }

  fun onEvent(event: LoginSignupEvent) {
    when (event) {
      is LoginSignupEvent.EnterLoginEmail -> {
        _loginEmailPassword.value = loginEmailPassword.value.copy(
          email = event.value
        )
      }
      is LoginSignupEvent.EnterLoginPassword -> {
        _loginEmailPassword.value = loginEmailPassword.value.copy(
          password = event.value
        )
      }
      is LoginSignupEvent.EnterSignupEmail -> {
        _signupEmailPassword.value = signupEmailPassword.value.copy(
          email = event.value
        )
      }
      is LoginSignupEvent.EnterSignupPassword -> {
        _signupEmailPassword.value = signupEmailPassword.value.copy(
          password = event.value
        )
      }
      is LoginSignupEvent.EnterSignupConfirmPassword -> {
        _signupEmailPassword.value = signupEmailPassword.value.copy(
          confirmPassword = event.value
        )
      }
      is LoginSignupEvent.LoginWithEmailAndPassword -> {
        viewModelScope.launch {
          try {
            loginSignupUsecases.loginWithEmailAndPassword(
              _loginEmailPassword.value.email,
              _loginEmailPassword.value.password
            )
            _eventFLow.emit(LoginSignupUIEvent.NavigateToMainScreen)
          } catch (e: LoginSignupArgumentException) {
            _eventFLow.emit(
              LoginSignupUIEvent.ShowText(
                message = e.message ?: "Couldn't log in"
              )
            )
          } catch (e: Exception) {
            _eventFLow.emit(
              LoginSignupUIEvent.ShowText(
                message = e.message ?: "Couldn't log in"
              )
            )
            resetUI (EventType.Login)
          }
        }
      }
      is LoginSignupEvent.SignupWithEmailAndPassword -> {
        viewModelScope.launch {
          try {
            loginSignupUsecases.signupWithEmailAndPassword(
              _signupEmailPassword.value.email,
              _signupEmailPassword.value.password,
              _signupEmailPassword.value.confirmPassword
            )
            _eventFLow.emit(
              LoginSignupUIEvent.ShowText(
                message = "Sign up successfully"
              )
            )
            resetUI(type = EventType.Signup)
          } catch (e: LoginSignupArgumentException) {
            _eventFLow.emit(
              LoginSignupUIEvent.ShowText(
                message = e.message ?: "Couldn't sign up"
              )
            )
          } catch (e: Exception) {
            _eventFLow.emit(
              LoginSignupUIEvent.ShowText(
                message = e.message ?: "Couldn't sign up"
              )
            )
            resetUI(type = EventType.Signup)
          }
        }
      }
      is LoginSignupEvent.LoginWithGoogle -> {
        viewModelScope.launch {
          try {
            loginSignupUsecases.loginWithGoogle
            _eventFLow.emit(LoginSignupUIEvent.NavigateToMainScreen)
          } catch (e: Exception) {
            _eventFLow.emit(
              LoginSignupUIEvent.ShowText(
                message = e.message ?: "Couldn't login with Google"
              )
            )
          }
        }
      }
      is LoginSignupEvent.LoginWithFaceBook -> {
        viewModelScope.launch {
          try {
            loginSignupUsecases.loginWithFacebook
            _eventFLow.emit(LoginSignupUIEvent.NavigateToMainScreen)
          } catch (e: Exception) {
            _eventFLow.emit(
              LoginSignupUIEvent.ShowText(
                message = e.message ?: "Couldn't login with Facebook"
              )
            )
          }
        }
      }
      is LoginSignupEvent.LoginWithTwitter -> {
        viewModelScope.launch {
          try {
            loginSignupUsecases.loginWithTwitter
            _eventFLow.emit(LoginSignupUIEvent.NavigateToMainScreen)
          } catch (e: Exception) {
            _eventFLow.emit(
              LoginSignupUIEvent.ShowText(
                message = e.message ?: "Couldn't login with Twitter"
              )
            )
          }
        }
      }
    }
  }
}