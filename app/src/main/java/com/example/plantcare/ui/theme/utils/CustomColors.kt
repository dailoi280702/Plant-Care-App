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
  primary = md_theme_light_primary,
  onPrimary = md_theme_light_onPrimary,
  primaryContainer = md_theme_light_primaryContainer,
  onPrimaryContainer = md_theme_light_onPrimaryContainer,
  secondary = md_theme_light_secondary,
  onSecondary = md_theme_light_onSecondary,
  secondaryContainer = md_theme_light_secondaryContainer,
  onSecondaryContainer = md_theme_light_onSecondaryContainer,
  tertiary = md_theme_light_tertiary,
  onTertiary = md_theme_light_onTertiary,
  tertiaryContainer = md_theme_light_tertiaryContainer,
  onTertiaryContainer = md_theme_light_onTertiaryContainer,
  error = md_theme_light_error,
  errorContainer = md_theme_light_errorContainer,
  onError = md_theme_light_onError,
  onErrorContainer = md_theme_light_onErrorContainer,
  background = md_theme_light_background,
  onBackground = md_theme_light_onBackground,
  surface = md_theme_light_surface,
  onSurface = md_theme_light_onSurface,
  surfaceVariant = md_theme_light_surfaceVariant,
  onSurfaceVariant = md_theme_light_onSurfaceVariant,
  outline = md_theme_light_outline,
  inverseOnSurface = md_theme_light_inverseOnSurface,
  inverseSurface = md_theme_light_inverseSurface,
  inversePrimary = md_theme_light_inversePrimary,
  shadow = md_theme_light_shadow,
  surfaceTint = md_theme_light_primary
)

fun darkCustomColors() = CustomColors(
  primary = md_theme_dark_primary,
  onPrimary = md_theme_dark_onPrimary,
  primaryContainer = md_theme_dark_primaryContainer,
  onPrimaryContainer = md_theme_dark_onPrimaryContainer,
  secondary = md_theme_dark_secondary,
  onSecondary = md_theme_dark_onSecondary,
  secondaryContainer = md_theme_dark_secondaryContainer,
  onSecondaryContainer = md_theme_dark_onSecondaryContainer,
  tertiary = md_theme_dark_tertiary,
  onTertiary = md_theme_dark_onTertiary,
  tertiaryContainer = md_theme_dark_tertiaryContainer,
  onTertiaryContainer = md_theme_dark_onTertiaryContainer,
  error = md_theme_dark_error,
  errorContainer = md_theme_dark_errorContainer,
  onError = md_theme_dark_onError,
  onErrorContainer = md_theme_dark_onErrorContainer,
  background = md_theme_dark_background,
  onBackground = md_theme_dark_onBackground,
  surface = md_theme_dark_surface,
  onSurface = md_theme_dark_onSurface,
  surfaceVariant = md_theme_dark_surfaceVariant,
  onSurfaceVariant = md_theme_dark_onSurfaceVariant,
  outline = md_theme_dark_outline,
  inverseOnSurface = md_theme_dark_inverseOnSurface,
  inverseSurface = md_theme_dark_inverseSurface,
  inversePrimary = md_theme_dark_inversePrimary,
  shadow = md_theme_dark_shadow,
  surfaceTint = md_theme_dark_primary
)

val LocalCustomColors = compositionLocalOf { lightCustomColors() }

val MaterialTheme.customColors: CustomColors
  @Composable
  get() = LocalCustomColors.current
