package com.example.plantcare.presentation.plant_task_card.components

import androidx.compose.foundation.layout.*
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlantTaskCard(
  task: PlantTask,
  showPlantName: Boolean = true,
  expanded: Boolean = false,
  viewModel: PlantTaskCardViewModel = hiltViewModel(),
  onPlantNameClick: () -> Unit,
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

  Card(
    modifier = Modifier
      .padding(horizontal = 16.dp),
//      .animateContentSize(
//        spring(
//          dampingRatio = Spring.DampingRatioLowBouncy,
//          stiffness = Spring.StiffnessHigh
//        )
//      ),
    elevation = CardDefaults.elevatedCardElevation(),
    onClick = onClick
  ) {
    Column {
      Row(
        modifier = Modifier
          .padding(8.dp)
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

        Column(
//          modifier = Modifier.padding(16.dp),
          horizontalAlignment = Alignment.End
        ) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
              text = importantText,
              style = MaterialTheme.typography.labelSmall,
              color = textColor
            )
            Spacer(modifier = Modifier.width(8.dp))
            Icon(
              modifier = Modifier
                .size(24.dp)
                .padding(end = 8.dp),
              painter = painterResource(id = iconId),
              contentDescription = null,
              tint = textColor
            )
          }
          if (expanded && showPlantName && plantName != null) {
            Box(
              modifier = Modifier
                .fillMaxWidth(),
              contentAlignment = Alignment.CenterEnd
            ) {
              TextButton(onClick = onPlantNameClick) {
                Text(
                  text = plantName,
                  color = MaterialTheme.colorScheme.tertiary,
                  maxLines = 1
                )
              }
            }
          }
        }
      }
    }
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
