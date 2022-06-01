package com.example.plantcare.presentation.todo_card.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.material.DismissState
import androidx.compose.material.DismissValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.plantcare.R
import com.example.plantcare.core.Utils
import com.example.plantcare.core.Utils.Companion.calculateDifferenceInDays
import com.example.plantcare.domain.model.Todo
import com.example.plantcare.ui.theme.fire
import com.example.plantcare.ui.theme.gold

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun PlantTaskCard(
  dismissState: DismissState,
  task: Todo,
  expanded: Boolean = false,
  onPlantNameClick: () -> Unit,
  onEditClick: () -> Unit,
  onDone: () -> Unit,
  onClick: () -> Unit
) {
  
  val importantText = when (task.important) {
    1 -> "important"
    2 -> "very important"
    else -> "not important"
  }
  val textColor = when (task.important) {
    1 -> gold
    2 -> fire
    else -> MaterialTheme.colorScheme.onSurface
  }
  val iconId = when (task.important) {
    1 -> R.drawable.ic_star_half_black_48dp
    2 -> R.drawable.ic_star_black_48dp
    else -> R.drawable.star_outline_black_48dp
  }
  
  val dayLeft = calculateDifferenceInDays(task.dueDay!!.toDate())
  
  Column(
    modifier = Modifier
      .padding(horizontal = 16.dp)
  ) {
    Card(
      elevation = CardDefaults.elevatedCardElevation(),
      onClick = onClick
    ) {
      Column(
        modifier = Modifier.padding(8.dp)
      ) {
        Row(
          modifier = Modifier
            .fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(checked = task.done, onCheckedChange = { onDone() })
            Column {
              Text(
                text = task.title!!,
                style = MaterialTheme.typography.titleMedium.copy(textDecoration = if (task.done) TextDecoration.LineThrough else null),
                overflow = TextOverflow.Ellipsis
              )
              Text(
                text = Utils.timeStampToString(task.dueDay!!),
                color = Utils.timeStampToColor(task.dueDay!!),
                style = MaterialTheme.typography.bodySmall
              )
            }
          }
          
          Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
              text = importantText,
              style = MaterialTheme.typography.labelSmall,
              color = textColor
            )
            
            Icon(
              painter = painterResource(id = iconId),
              contentDescription = null,
              tint = textColor,
              modifier = Modifier
                .padding(horizontal = 8.dp)
                .size(24.dp)
            )
          }
        }
      }
    }
    
    AnimatedVisibility(visible = expanded && dismissState.targetValue == DismissValue.Default) {
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .padding(horizontal = 12.dp)
      ) {
        Row(
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically,
          modifier = Modifier
            .fillMaxWidth()
        ) {
          TextButton(onClick = onPlantNameClick) {
            Text(
              text = task.plantName ?: "Undefined plant",
              color = MaterialTheme.colorScheme.tertiary,
              maxLines = 1
            )
          }
          IconButton(onClick = onEditClick) {
            Icon(
              painter = painterResource(id = R.drawable.ic_edit_outline),
              contentDescription = null,
              modifier = Modifier
                .size(24.dp)
            )
          }
        }
        Divider(color = MaterialTheme.colorScheme.surfaceVariant)
      }
    }
  }
}

