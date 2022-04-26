package com.example.plantcare.presentation.login_signup.utils

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp

@Composable
fun LoginIconButton(
  icon: Painter,
  contentDescription: String,
  backgroundColor: Color,
  tint: Color = Color.White,
  onClick: () -> Unit
) {
  IconButton(
    onClick = onClick,
    modifier = Modifier
      .width(72.dp)
      .clip(shape = RoundedCornerShape(50))
      .background(backgroundColor),
  ) {
    Icon(
      modifier = Modifier.size(24.dp).fillMaxWidth(),
      painter = icon,
      contentDescription = contentDescription,
      tint = tint
    )
  }
}