package com.example.plantcare.presentation.todo_dashboard.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProgressCard(
  total: Int,
  left: Int,
  backgroundIndicatorColor: Color = MaterialTheme.colorScheme.secondary,
  foregroundIndicatorColor: Color = MaterialTheme.colorScheme.primary,
  isVerticalCard: Boolean = true,
  modifier: Modifier = Modifier,
  content: @Composable ColumnScope.() -> Unit
) {

}