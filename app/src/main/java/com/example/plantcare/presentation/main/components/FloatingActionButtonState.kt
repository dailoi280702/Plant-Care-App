package com.example.plantcare.presentation.main.components

data class FloatingActionButtonState(
  val icon: Int = -1,
  val contentDescription: String = "",
  val onClick: () -> Unit = {}
)