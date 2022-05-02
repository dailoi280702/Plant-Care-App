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
  val error: Color,
  val errorContainer: Color,
  val onError: Color,
  val onErrorContainer: Color
)

fun lightCustomColors() = CustomColors(
  primary = Green40,
  primaryContainer = Green90,
  secondary = LightGreen40,
  secondaryContainer = LightGreen90,
  tertiary = LightBlue40,
  tertiaryContainer = LightBlue90,
  surface = Gray99,
  surfaceVariant = Gray90,
  background = Gray99,
  onPrimary = White,
  onPrimaryContainer = Green10,
  onSecondary = White,
  onSecondaryContainer = LightGreen10,
  onTertiary = White,
  onTertiaryContainer = LightBlue10,
  onSurface = Gray10,
  onSurfaceVariant = Gray30,
  onBackground = Gray10,
  outline = Gray50,
  shadow = Black,
  surfaceTint = Green40,
  inverseSurface = Gray20,
  inverseOnSurface = Gray90,
  inversePrimary = Green80,
  error = Red40,
  errorContainer = Red90,
  onError = White,
  onErrorContainer = Red10
)

fun darkCustomColors() = CustomColors(
  primary = Green80,
  primaryContainer = Green30,
  secondary = LightGreen80,
  secondaryContainer = LightGreen30,
  tertiary = LightBlue80,
  tertiaryContainer = LightBlue30,
  surface = Gray10,
  surfaceVariant = Gray30,
  background = Gray10,
  onPrimary = Green20,
  onPrimaryContainer = Green90,
  onSecondary = LightGreen20,
  onSecondaryContainer = LightGreen90,
  onTertiary = LightBlue20,
  onTertiaryContainer = LightBlue90,
  onSurface = Gray90,
  onSurfaceVariant = Gray80,
  onBackground = Gray90,
  outline = Gray60,
  shadow = Black,
  surfaceTint = Green40,
  inverseSurface = Gray90,
  inverseOnSurface = Gray20,
  inversePrimary = Green40,
  error = Red80,
  errorContainer = Red30,
  onError = Red20,
  onErrorContainer = Red80
)

val LocalCustomColors = compositionLocalOf { lightCustomColors() }

val MaterialTheme.customColors: CustomColors
  @Composable
  get() = LocalCustomColors.current
