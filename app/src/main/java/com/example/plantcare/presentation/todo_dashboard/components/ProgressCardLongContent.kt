package com.example.plantcare.presentation.todo_dashboard.components;

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable;
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.plantcare.ui.theme.fire
import com.example.plantcare.ui.theme.gold
import com.example.plantcare.ui.theme.utils.customColors

@Composable
fun ProgressCardLongContent(
  total: Int,
  done: Int,
  important: Int,
  veryImportant: Int,
  overdue: Int,
) {
  Text(
    text = "All todos",
    style = MaterialTheme.typography.titleLarge.copy(color = MaterialTheme.colorScheme.onSurfaceVariant)
  )
  Spacer(modifier = Modifier.height(8.dp))
  Text(
    text = "$total todo${if (total > 1) "s" else ""}, ${if (total == done && total != 0) "all" else done.toString()} done",
    style = MaterialTheme.typography.titleMedium,
    color = MaterialTheme.colorScheme.onSurfaceVariant
  )
  if (important > 0) {
    Text(
      text = "$important important",
      style = MaterialTheme.typography.titleMedium,
      color = customColors.yellow
    )
  }
  if (veryImportant > 0) {
    Text(
      text = "$veryImportant very important",
      style = MaterialTheme.typography.titleMedium,
      color = customColors.orange
    )
  }
  if (overdue > 0) {
    Text(
      text = "$overdue overdue",
      style = MaterialTheme.typography.titleMedium,
      color = MaterialTheme.colorScheme.error
    )
  }
}
