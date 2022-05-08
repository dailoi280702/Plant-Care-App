package com.example.plantcare.presentation.add_edit_plant.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InfoSection(
  name: String,
  description: String,
  onNameChange: (String) -> Unit,
  onDescriptionChange: (String) -> Unit
) {
  Card(
    modifier = Modifier
      .padding(16.dp)
      .fillMaxWidth(),
    elevation = CardDefaults.elevatedCardElevation()
  ) {
    Column(
      Modifier
        .fillMaxWidth()
        .padding(horizontal = 8.dp)
    ) {
      TextField(
        modifier = Modifier
          .offset(y = 8.dp)
          .fillMaxWidth(),
        value = name,
        onValueChange = {
          onNameChange(it)
        },
        singleLine = true,
        placeholder = {
          Text(text = "Name", color = MaterialTheme.colorScheme.onSurface)
        },
        textStyle = MaterialTheme.typography.headlineSmall,
        shape = RectangleShape,
        colors = TextFieldDefaults.textFieldColors(
          cursorColor = MaterialTheme.colorScheme.onSurface,
          disabledLabelColor = MaterialTheme.colorScheme.secondary,
          focusedIndicatorColor = Color.Transparent,
          unfocusedIndicatorColor = Color.Transparent
        )
      )
      TextField(
        modifier = Modifier
          .offset(y = (-8).dp)
          .fillMaxWidth(),
        value = description,
        onValueChange = {
          onDescriptionChange(it)
        },
        singleLine = false,
        placeholder = {
          Text(text = "Description", color = MaterialTheme.colorScheme.onSurface)
        },
        textStyle = MaterialTheme.typography.bodySmall,
        shape = RectangleShape,
        colors = TextFieldDefaults.textFieldColors(
//          backgroundColor = Color.Transparent,
          cursorColor = MaterialTheme.colorScheme.onSurface,
          disabledLabelColor = MaterialTheme.colorScheme.secondary,
          focusedIndicatorColor = Color.Transparent,
          unfocusedIndicatorColor = Color.Transparent
        )
      )
    }
  }
}