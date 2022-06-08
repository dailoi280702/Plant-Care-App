package com.example.plantcare.presentation.todo_dashboard.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ProgressCardShortContent(
  text: String,
  total: Int,
  left: Int
){
  Text(
    text = text,
    style = MaterialTheme.typography.titleLarge.copy(color = MaterialTheme.colorScheme.onSurfaceVariant)
  )
  Spacer(modifier = Modifier.height(8.dp))
  Text(
    text = "$total todo${if (total > 1) "s" else ""}, $left left",
    style = MaterialTheme.typography.titleMedium.copy(color = MaterialTheme.colorScheme.onSurfaceVariant)
  )
}