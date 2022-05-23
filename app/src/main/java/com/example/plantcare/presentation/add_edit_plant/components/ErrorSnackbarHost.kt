package com.example.plantcare.presentation.add_edit_plant.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ErrorSnackbarHost(
  snackbarHostState: SnackbarHostState
) {

  SnackbarHost(
    hostState = snackbarHostState,
    snackbar = {
      Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Card(
          shape = RoundedCornerShape(8.dp),
          modifier = Modifier
            .padding(16.dp)
            .wrapContentSize(),
          elevation = CardDefaults.elevatedCardElevation()
        ) {
          Column(
            modifier = Modifier.padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
          ) {
            Text(
              text = it.visuals.message,
              color = MaterialTheme.colorScheme.error
            )
          }
        }
      }
    }
  )
}