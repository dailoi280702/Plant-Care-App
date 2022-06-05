package com.example.plantcare.presentation.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.plantcare.domain.use_case.authentication.LoginSignupUsecases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewmodel @Inject constructor(
  private val authenticationUseCases: LoginSignupUsecases
) : ViewModel() {
  
  fun signOut() {
    viewModelScope.launch {
      authenticationUseCases.logout()
    }
  }
}