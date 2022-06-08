package com.example.plantcare.presentation.login.utils

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
  Button(
    onClick = onClick,
    colors = ButtonDefaults.buttonColors(
      contentColor = tint,
      containerColor =  backgroundColor
    ),
    modifier = Modifier
      .width(72.dp)
  ) {
    Icon(
      modifier = Modifier
        .size(24.dp)
        .fillMaxWidth(),
      painter = icon,
      contentDescription = contentDescription,
      tint = tint
    )
  }
}