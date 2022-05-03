package com.example.plantcare.presentation.add_edit_plant.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterialApi::class)
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
    exit = fadeOut() + slideOutVertically(
      targetOffsetY = { it },
      animationSpec = tween(
        easing = LinearEasing
      )
    )
  ) {
    Row(
      modifier = Modifier.fillMaxWidth(),
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.End
    ) {
      Surface(
        shape = RoundedCornerShape(8.dp),
        color = containerColor,
        elevation = 8.dp
      ) {
        Text(
          text = text,
          color = onContainerColor,
          modifier = Modifier
            .padding(vertical = 4.dp, horizontal = 8.dp)
        )
      }

      Surface(
        onClick = {
          onClick()
        },
        color = containerColor,
        shape = RoundedCornerShape(12.dp),
        elevation = 8.dp,
        modifier = Modifier.padding(horizontal = 24.dp)
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