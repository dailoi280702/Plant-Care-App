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
  
  val percentage: Float = if (total == 0) 0f else (1 - left.toFloat() / total.toFloat())
  val circularProgressbar: @Composable () -> Unit = {
    CircularProgressbar(
      backgroundIndicatorColor = backgroundIndicatorColor,
      foregroundIndicatorColor = foregroundIndicatorColor,
      textColor = MaterialTheme.colorScheme.onSurfaceVariant,
      percentage = percentage
    )
  }
  
  Card(
    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
    elevation = CardDefaults.cardElevation(),
    shape = RoundedCornerShape(28.dp),
    modifier = modifier
      .fillMaxWidth()
      .padding(8.dp)
  ) {
    if (isVerticalCard) {
      Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(16.dp)
      ) {
        circularProgressbar()
        Spacer(modifier = Modifier.height(8.dp))
        content()
      }
    } else {
      Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(16.dp)
      ) {
        Box(
          modifier = Modifier
            .height(160.dp)
            .aspectRatio(1f)
        ) {
          circularProgressbar()
        }
        Spacer(modifier = Modifier.width(8.dp))
        Column {
          content()
        }
      }
    }
  }
}