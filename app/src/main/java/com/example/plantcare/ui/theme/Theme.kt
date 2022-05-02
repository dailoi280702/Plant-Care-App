package com.example.plantcare.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import com.example.plantcare.ui.theme.utils.LocalCustomColors
import com.example.plantcare.ui.theme.utils.darkCustomColors
import com.example.plantcare.ui.theme.utils.lightCustomColors

private val DarkColorPalette = darkColors(
  primary = LightGreen80,
  primaryVariant = LightGreen30,
  secondary = Lime80,
  secondaryVariant = LightGreen30,
  background = Gray10,
  surface = Gray10,
  onPrimary = Gray20,
  onSecondary = Gray20,
  onBackground = Gray90,
  onSurface = Gray90,
  error = Red80,
  onError = Red20
)

private val LightColorPalette = lightColors(
  primary = LightGreen40,
  primaryVariant = LightGreen90,
  secondary = Lime40,
  secondaryVariant = LightGreen90,
  background = Gray99,
  surface = Gray99,
  onPrimary = White,
  onSecondary = White,
  onBackground = Gray10,
  onSurface = Gray10,
  error = Red40,
)

private val LightColorPaletteCustom = lightCustomColors()

private val DarkColorPaletteCustom = darkCustomColors()

@Composable
fun PlantCareTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
  val colors = if (darkTheme) {
    DarkColorPalette
  } else {
    LightColorPalette
  }
  val customColors = if (darkTheme) {
    DarkColorPaletteCustom
  } else {
    LightColorPaletteCustom
  }

  CompositionLocalProvider(
    LocalCustomColors provides customColors
  ) {
    MaterialTheme(
      colors = colors,
      typography = Typography,
      shapes = Shapes,
      content = content,
    )
  }


}