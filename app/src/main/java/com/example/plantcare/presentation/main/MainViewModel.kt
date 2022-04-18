package com.example.plantcare.presentation.main

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.plantcare.presentation.utils.Screens

class MainViewModel : ViewModel() {
  private val _currentScreen = mutableStateOf<Screens?>(null)
  val currentScreen: State<Screens?> = _currentScreen

  fun setCurrentScreen(screen: Screens) {
    _currentScreen.value = screen
  }
}