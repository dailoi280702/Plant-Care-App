package com.example.plantcare.presentation.main

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.plantcare.presentation.main.components.FloatingActionButtonState
import com.example.plantcare.presentation.utils.Screens

class MainViewModel : ViewModel() {
  private val _currentScreen = mutableStateOf<Screens?>(null)
  val currentScreen: State<Screens?> = _currentScreen

//  private val _floatingActionButton = mutableStateOf<@Composable () -> Unit>({})
//  val floatingActionButton: State<@Composable () -> Unit> = _floatingActionButton

  private val _fbaState = mutableStateOf(FloatingActionButtonState())
  val fbaState: State<FloatingActionButtonState> = _fbaState

  fun setCurrentScreen(screen: Screens) {
    _currentScreen.value = screen
//    setFloatingActionButton {  }
  }

//  fun setFloatingActionButton(fba: @Composable () -> Unit) {
//    _floatingActionButton.value = fba
//  }

  fun setFloatingActionButton(icon: Int, contentDescription: String, onClick: () -> Unit) {
    _fbaState.value = fbaState.value.copy(
      icon = icon,
      contentDescription = contentDescription,
      onClick = onClick
    )
  }
}