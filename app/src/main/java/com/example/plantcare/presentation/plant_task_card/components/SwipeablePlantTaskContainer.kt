package com.example.plantcare.presentation.plant_task_card.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SwipeablePlantTaskContainer(
  dismissState: DismissState,
  card: @Composable () -> Unit
) {

  val color by animateColorAsState(
    targetValue = if (dismissState.targetValue == DismissValue.Default) MaterialTheme.colorScheme.background else when (dismissState.dismissDirection) {
      DismissDirection.StartToEnd -> MaterialTheme.colorScheme.errorContainer
      else -> MaterialTheme.colorScheme.background
    }
  )
  val direction = dismissState.dismissDirection
  val scale by animateFloatAsState(targetValue = if (dismissState.targetValue == DismissValue.Default) 0.8f else 1.2f)

  SwipeToDismiss(
    modifier = Modifier
      .padding(vertical = 4.dp),
    dismissThresholds = { FractionalThreshold(0.3f) },
    state = dismissState,
    background = {
      Box(
        modifier = Modifier
          .fillMaxSize()
          .background(color),
        contentAlignment = Alignment.CenterStart
      ) {
        if (direction == DismissDirection.StartToEnd) {
          Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier.fillMaxWidth(),
          ) {
            Spacer(modifier = Modifier.width(8.dp))
            Icon(
              imageVector = Icons.Default.Delete,
              contentDescription = null,
              tint = MaterialTheme.colorScheme.onErrorContainer,
              modifier = Modifier
                .padding(16.dp)
                .scale(scale)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
              text = "Delete",
              textAlign = TextAlign.Center,
              color = MaterialTheme.colorScheme.onErrorContainer
            )
          }
        }
      }
    },
    /**** Dismiss Content */
    /**** Dismiss Content */
    dismissContent = {
      card()
    },
    /*** Set Direction to dismiss */
    /*** Set Direction to dismiss */
    directions = setOf(DismissDirection.StartToEnd)
  )
}
