package com.example.plantcare.presentation.login

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.plantcare.data.utils.DataState
import com.example.plantcare.domain.use_case.authentication.LoginSignupUsecases
import com.example.plantcare.domain.utils.LoginSignupArgumentException
import com.example.plantcare.presentation.main.utils.Screens
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@HiltViewModel
class AuthenticationViewModel @Inject constructor(
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
  
  private val _dataState = mutableStateOf<DataState<Void?>?>(null)
  val dataState: State<DataState<Void?>?> = _dataState
  
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
  
  private fun loginWithEmailAndPassword() {
    viewModelScope.launch {
      try {
        loginSignupUsecases.loginWithEmailAndPassword(
          _loginEmailPassword.value.email,
          _loginEmailPassword.value.password
        ).collectLatest {
          _dataState.value = it
          if (it is DataState.Success) {
            _eventFLow.emit(LoginSignupUIEvent.Navigate(Screens.MainScreens.Home))
          }
          if (it is DataState.Error) {
            _eventFLow.emit(
              LoginSignupUIEvent.ShowText(
                message = it.message
              )
            )
            resetUI(EventType.Login)
          }
        }
      } catch (e: Exception) {
        _eventFLow.emit(
          LoginSignupUIEvent.ShowText(
            message = e.message ?: "Log in failed!"
          )
        )
        resetUI(EventType.Login)
      }
    }
  }
  
  private fun signUp() {
    viewModelScope.launch {
      try {
        loginSignupUsecases.signupWithEmailAndPassword(
          _signupEmailPassword.value.email,
          _signupEmailPassword.value.password,
          _signupEmailPassword.value.confirmPassword
        ).collectLatest {
          _dataState.value = it
          if (it is DataState.Success) {
            _eventFLow.emit(
              LoginSignupUIEvent.ShowText(
                message = "Sign up successfully"
              )
            )
            resetUI(type = EventType.Signup)
          }
          if (it is DataState.Error) {
            _eventFLow.emit(
              LoginSignupUIEvent.ShowText(
                message = it.message
              )
            )
            resetUI(type = EventType.Signup)
          }
        }
        resetUI(type = EventType.Signup)
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
  
  private fun loginWithGoogle(credential: AuthCredential) {
    viewModelScope.launch {
      try {
        loginSignupUsecases.loginWithGoogle(credential).collectLatest {
          _dataState.value = it
          if (it is DataState.Success) {
            _eventFLow.emit(LoginSignupUIEvent.Navigate(Screens.MainScreens.Home))
          }
          if (it is DataState.Error) {
            _eventFLow.emit(
              LoginSignupUIEvent.ShowText(
                message = it.message
              )
            )
          }
        }
      } catch (e: Exception) {
        _eventFLow.emit(
          LoginSignupUIEvent.ShowText(
            message = e.message ?: "Log in with Google failed!"
          )
        )
      }
    }
  }
  
  private fun loginWithFacebook() {
    viewModelScope.launch {
      try {
        loginSignupUsecases.loginWithFacebook().collectLatest {
          _dataState.value = it
          if (it is DataState.Success) {
            _eventFLow.emit(LoginSignupUIEvent.Navigate(Screens.MainScreens.Home))
          }
          if (it is DataState.Error) {
            _eventFLow.emit(
              LoginSignupUIEvent.ShowText(
                message = it.message
              )
            )
          }
        }
      } catch (e: Exception) {
        _eventFLow.emit(
          LoginSignupUIEvent.ShowText(
            message = e.message ?: "Log in with Facebook failed!"
          )
        )
      }
    }
  }
  
  private fun loginWithTwitter() {
    viewModelScope.launch {
      try {
        loginSignupUsecases.loginWithTwitter().collectLatest {
          _dataState.value = it
          if (it is DataState.Success) {
            _eventFLow.emit(LoginSignupUIEvent.Navigate(Screens.MainScreens.Home))
          }
          if (it is DataState.Error) {
            _eventFLow.emit(
              LoginSignupUIEvent.ShowText(
                message = it.message
              )
            )
          }
        }
      } catch (e: Exception) {
        _eventFLow.emit(
          LoginSignupUIEvent.ShowText(
            message = e.message ?: "Log in with Twitter failed!"
          )
        )
      }
    }
  }
  
  fun isUserLogedin(): Boolean {
    return loginSignupUsecases.isUserLogedin()
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
        loginWithEmailAndPassword()
      }
      is LoginSignupEvent.SignupWithEmailAndPassword -> {
        signUp()
      }
      is LoginSignupEvent.LoginWithGoogle -> {
        loginWithGoogle(event.value)
      }
      is LoginSignupEvent.LoginWithFaceBook -> {
        loginWithFacebook()
      }
      is LoginSignupEvent.LoginWithTwitter -> {
        loginWithTwitter()
      }
      is LoginSignupEvent.SignOut -> {
        viewModelScope.launch {
          try {
            loginSignupUsecases.logout()
            _eventFLow.emit(
              LoginSignupUIEvent.Navigate(Screens.LoginSignupScreen)
            )
          } catch (e: Exception) {
            _eventFLow.emit(
              LoginSignupUIEvent.ShowText(
                message = e.message ?: "Couldn't log out"
              )
            )
          }
        }
      }
    }
  }
}