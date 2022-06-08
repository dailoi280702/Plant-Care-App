package com.example.plantcare.presentation.plant_detail.components

import androidx.compose.animation.*
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterialApi::class, ExperimentalMaterial3Api::class,
  ExperimentalAnimationApi::class
)
@Composable
fun HiddenFloatingActionButton(
  visible: Boolean,
  text: String,
  containerColor: Color,
  onContainerColor: Color,
  painter: Painter,
  onClick: () -> Unit
) {
  AnimatedVisibility(
    visible = visible,
    exit = scaleOut() + slideOutHorizontally(targetOffsetX = {it}) + fadeOut()
  ) {
    Row(
      modifier = Modifier.fillMaxWidth(),
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.End
    ) {
      Surface(
        shape = RoundedCornerShape(8.dp),
        color = containerColor,
      ) {
        Text(
          text = text,
          color = onContainerColor,
          modifier = Modifier
            .padding(vertical = 4.dp, horizontal = 8.dp)
        )
      }

      Surface(
        color = containerColor,
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier.padding(horizontal = 20.dp),
        onClick = {
          onClick()
        },
        tonalElevation = 8.dp
      ) {
        Icon(
          painter = painter,
          contentDescription = text,
          tint = onContainerColor,
          modifier = Modifier
            .padding(8.dp)
            .size(24.dp)
        )
      }
    }
  }
}