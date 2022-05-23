package com.example.plantcare.presentation.add_edit_plant.components

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.material.ContentAlpha
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp

@Composable
fun ExpandableSurface(
  title: String,
  modifier: Modifier = Modifier,
  expanded: Boolean,
  onClick: () -> Unit,
  content: @Composable () -> Unit
) {

  val rotationState by animateFloatAsState(
    targetValue = if (expanded) 180f else 0f
  )

  Column(
    modifier = modifier
      .fillMaxWidth()
      .animateContentSize(
        animationSpec = tween(
          durationMillis = 200,
          easing = LinearOutSlowInEasing
        )
      )
  ) {
    Row(
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.SpaceBetween,
      modifier = Modifier
        .padding(horizontal = 16.dp)
        .fillMaxWidth()
    ) {
      Text(
        text = title,
        maxLines = 1,
        fontStyle = MaterialTheme.typography.headlineMedium.fontStyle,
        overflow = TextOverflow.Ellipsis,
        color = MaterialTheme.colorScheme.onSurface
      )
      IconButton(
        modifier = Modifier
          .alpha(ContentAlpha.medium)
          .rotate(rotationState),
        onClick = {
          onClick()
        }) {
        Icon(
          imageVector = Icons.Default.ArrowDropDown,
          contentDescription = "Drop-Down Arrow"
        )
      }
    }

    if (expanded) {
      content()
    }
  }
}