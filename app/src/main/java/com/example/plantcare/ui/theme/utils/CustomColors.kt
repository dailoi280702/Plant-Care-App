package com.example.plantcare.ui.theme.utils

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.Color
import com.example.plantcare.ui.theme.*

data class CustomColors(
  val yellow: Color,
  val orange: Color,
)

fun lightCustomColors() = CustomColors(
  yellow = yellow_light,
  orange = oragne_light
)

fun darkCustomColors() = CustomColors(
  yellow = yellow_dark,
  orange = oragne_dark
)

val LocalCustomColors = compositionLocalOf { lightCustomColors() }

val customColors: CustomColors
  @Composable
  get() = LocalCustomColors.current
