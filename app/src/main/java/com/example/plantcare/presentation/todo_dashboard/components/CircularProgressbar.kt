package com.example.plantcare.presentation.todo_dashboard.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun CircularProgressbar(
  backgroundIndicatorColor: Color,
  foregroundIndicatorColor: Color,
  textColor: Color,
  percentage: Float = 0f,
  indicatorThickness: Dp = 16.dp,
) {
  
  var numberR by remember {
    mutableStateOf(0f)
  }
  
  val animateNumber = animateFloatAsState(
    targetValue = numberR * 100,
    animationSpec = tween(
      durationMillis = 1000
    )
  )
  
  LaunchedEffect(percentage) {
    numberR = percentage
  }
  
  Box(
    contentAlignment = Alignment.Center,
    modifier = Modifier
      .padding(16.dp)
      .fillMaxWidth()
      .aspectRatio(1f)
  ) {
    Canvas(
      modifier = Modifier
        .fillMaxSize()
    ) {
      drawCircle(
        color = backgroundIndicatorColor.copy(alpha = 0.5f),
        style = Stroke(width = (indicatorThickness - 4.dp).toPx(), cap = StrokeCap.Round)
      )
      
      drawArc(
        color = foregroundIndicatorColor,
        startAngle = -90f,
        sweepAngle = animateNumber.value / 100f * 360f,
        useCenter = false,
        style = Stroke((indicatorThickness).toPx(), cap = StrokeCap.Round)
      )
    }
    Text(
      text = "${animateNumber.value.toInt()}%",
      style = MaterialTheme.typography.headlineMedium.copy(
        fontWeight = FontWeight.Bold,
        color = textColor,
      )
    )
  }
}
