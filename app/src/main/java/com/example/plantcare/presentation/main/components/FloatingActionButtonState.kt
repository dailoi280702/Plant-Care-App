package com.example.plantcare.presentation.main.components

data class FloatingActionButtonState(
  val icon: Int = -1,
  val contentDescription: String = "",
  val rotation: Float = 0f,
  val onClick: () -> Unit = {}
)