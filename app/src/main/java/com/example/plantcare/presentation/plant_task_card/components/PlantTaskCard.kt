package com.example.plantcare.presentation.plant_task_card.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.material.DismissState
import androidx.compose.material.DismissValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.plantcare.R
import com.example.plantcare.core.Utils.Companion.calculateDifferenceInDays
import com.example.plantcare.domain.model.PlantTask
import com.example.plantcare.presentation.plant_task_card.PlantTaskCardViewModel
import com.example.plantcare.ui.theme.fire
import com.example.plantcare.ui.theme.gold

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun PlantTaskCard(
  dismissState: DismissState,
  task: PlantTask,
  showPlantName: Boolean = true,
  expanded: Boolean = false,
  viewModel: PlantTaskCardViewModel = hiltViewModel(),
  onPlantNameClick: () -> Unit,
  onEditClick: () -> Unit,
  onDone: () -> Unit,
  onClick: () -> Unit
) {

  LaunchedEffect(Unit) {
    viewModel.getPlantName(task.plantId!!)
  }

  val plantName = viewModel.plantName.value
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
    1 -> R.drawable.ic_star_half
    2 -> R.drawable.ic_star
    else -> R.drawable.ic_star_outline
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
              if (task.overDue) {
                Text(
                  text = "overdue $dayLeft day${if (dayLeft == 1L) "" else "s"}",
                  style = MaterialTheme.typography.bodySmall,
                  color = MaterialTheme.colorScheme.error
                )
              } else {
                Text(
                  text = if (dayLeft == 0L) "Today" else "$dayLeft day${if (dayLeft == 1L) "" else "s"} left",
                  style = MaterialTheme.typography.bodySmall
                )
              }
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

    AnimatedVisibility(visible = expanded && dismissState.targetValue == DismissValue.Default && plantName != null) {
      Column(
        modifier = Modifier
          .fillMaxWidth()
      ) {
        Row(
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically,
          modifier = Modifier
            .fillMaxWidth()
        ) {
          TextButton(onClick = onPlantNameClick) {
            Text(
              text = plantName ?: "",
              color = MaterialTheme.colorScheme.tertiary,
              maxLines = 1
            )
          }
          IconButton(onClick = onEditClick) {
            Icon(
              painter = painterResource(id = R.drawable.ic_edit_outline),
              contentDescription = null,
              modifier = Modifier
                .padding(horizontal = 8.dp)
                .size(24.dp)
            )
          }
        }
        Divider()
      }
    }
//    if (expanded && showPlantName && plantName != null) {
//
//    }
  }
}
//            navController.navigate(Screens.AddPlantScreen.route + "?plantId=${task.plantId}") {
//              navController.graph.startDestinationRoute?.let { route ->
//                popUpTo(route) {
//                  saveState = true
//                }
//              }
//              launchSingleTop = true
//              restoreState = true
//            }
