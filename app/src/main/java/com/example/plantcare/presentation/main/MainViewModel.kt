package com.example.plantcare.presentation.main

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.plantcare.presentation.main.components.FloatingActionButtonState
import com.example.plantcare.presentation.main.utils.Screens
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {
  private val _currentScreen = mutableStateOf<Screens?>(null)
  val currentScreen: State<Screens?> = _currentScreen

  private val _fbaState = mutableStateOf(FloatingActionButtonState())
  val fbaState: State<FloatingActionButtonState> = _fbaState

  fun setCurrentScreen(screen: Screens) {
    _currentScreen.value = screen
  }

  fun setFloatingActionButton(
    icon: Int,
    contentDescription: String,
    rotation: Float = 0f,
    onClick: () -> Unit
  ) {
    _fbaState.value = fbaState.value.copy(
      icon = icon,
      contentDescription = contentDescription,
      rotation = rotation,
      onClick = onClick
    )
  }
}