package com.example.plantcare.ui.theme.utils

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.Color
import com.example.plantcare.ui.theme.*

data class CustomColors(
  val primary: Color,
  val primaryContainer: Color,
  val secondary: Color,
  val secondaryContainer: Color,
  val tertiary: Color,
  val tertiaryContainer: Color,
  val surface: Color,
  val surfaceVariant: Color,
  val background: Color,
  val onPrimary: Color,
  val onPrimaryContainer: Color,
  val onSecondary: Color,
  val onSecondaryContainer: Color,
  val onTertiary: Color,
  val onTertiaryContainer: Color,
  val onSurface: Color,
  val onSurfaceVariant: Color,
  val onBackground: Color,
  val outline: Color,
  val shadow: Color,
  val surfaceTint: Color,
  val inverseSurface: Color,
  val inverseOnSurface: Color,
  val inversePrimary: Color,
)

fun lightCustomColors() = CustomColors(
  primary = LightGreen40,
  primaryContainer = LightGreen90,
  secondary = Green40,
  secondaryContainer = Green90,
  tertiary = LightBlue40,
  tertiaryContainer = LightBlue90,
  surface = Gray99,
  surfaceVariant = Gray90,
  background = Gray99,
  onPrimary = White,
  onPrimaryContainer = LightGreen10,
  onSecondary = White,
  onSecondaryContainer = Green10,
  onTertiary = White,
  onTertiaryContainer = LightBlue10,
  onSurface = Gray10,
  onSurfaceVariant = Gray30,
  onBackground = Gray10,
  outline = Gray50,
  shadow = Black,
  surfaceTint = LightGreen40,
  inverseSurface = Gray20,
  inverseOnSurface = Gray90,
  inversePrimary = LightGreen80,
)

fun darkCustomColors() = CustomColors(
  primary = LightGreen80,
  primaryContainer = LightGreen30,
  secondary = Green80,
  secondaryContainer = Green30,
  tertiary = LightBlue80,
  tertiaryContainer = LightBlue30,
  surface = Gray10,
  surfaceVariant = Gray30,
  background = Gray10,
  onPrimary = LightGreen20,
  onPrimaryContainer = LightGreen90,
  onSecondary = Green20,
  onSecondaryContainer = Green90,
  onTertiary = LightBlue20,
  onTertiaryContainer = LightBlue90,
  onSurface = Gray90,
  onSurfaceVariant = Gray80,
  onBackground = Gray90,
  outline = Gray60,
  shadow = Black,
  surfaceTint = LightGreen40,
  inverseSurface = Gray90,
  inverseOnSurface = Gray20,
  inversePrimary = LightGreen40,
)

val LocalCustomColors = compositionLocalOf { lightCustomColors() }

val MaterialTheme.customColors: CustomColors
  @Composable
  get() = LocalCustomColors.current
