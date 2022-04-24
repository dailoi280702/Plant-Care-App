package com.example.plantcare.presentation.main

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.plantcare.presentation.utils.Screens

class MainViewModel : ViewModel() {
  private val _currentScreen = mutableStateOf<Screens?>(null)
  val currentScreen: State<Screens?> = _currentScreen

  private val _floatingActionButton = mutableStateOf<@Composable () -> Unit>({})
  val floatingActionButton: State<@Composable () -> Unit> = _floatingActionButton

  fun setCurrentScreen(screen: Screens) {
    _currentScreen.value = screen
    setFloatingActionButton {  }
  }

  fun setFloatingActionButton(fba: @Composable () -> Unit) {
    _floatingActionButton.value = fba
  }
}