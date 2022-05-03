package com.example.plantcare.presentation.add_edit_plant.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.plantcare.ui.theme.utils.customColors

@Composable
fun ErrorSnackbarHost(
  snackbarHostState: SnackbarHostState
) {

  SnackbarHost(
    hostState = snackbarHostState,
    snackbar = { snackbarData: SnackbarData ->
      Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Card(
          shape = RoundedCornerShape(8.dp),
          modifier = Modifier
            .padding(16.dp)
            .wrapContentSize(),
          elevation = 8.dp
        ) {
          Column(
            modifier = Modifier.padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
          ) {
            Text(
              text = snackbarData.message,
              color = MaterialTheme.customColors.error
            )
          }
        }
      }
    }
  )
}