package com.example.plantcare.presentation.add_edit_plant.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.plantcare.ui.theme.utils.customColors

@Composable
fun InfoSection(
  name: String,
  description: String,
  onNameChange: (String) -> Unit,
  onDescriptionChange: (String) -> Unit
) {
  Surface(
    modifier = Modifier
      .padding(16.dp)
      .fillMaxWidth(),
    color = MaterialTheme.customColors.surface,
    elevation = 8.dp,
    shape = RoundedCornerShape(12.dp)
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
          Text(text = "Name", color = MaterialTheme.customColors.onSurface)
        },
        textStyle = MaterialTheme.typography.body1.copy(
          fontWeight = FontWeight.Medium
        ),
        shape = RectangleShape,
        colors = TextFieldDefaults.textFieldColors(
          backgroundColor = Color.Transparent,
          cursorColor = MaterialTheme.colors.onSurface,
          disabledLabelColor = MaterialTheme.customColors.secondary,
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
          Text(text = "Description", color = MaterialTheme.customColors.onSurface)
        },
        textStyle = MaterialTheme.typography.body2,
        shape = RectangleShape,
        colors = TextFieldDefaults.textFieldColors(
          backgroundColor = Color.Transparent,
          cursorColor = MaterialTheme.colors.onSurface,
          disabledLabelColor = MaterialTheme.customColors.secondary,
          focusedIndicatorColor = Color.Transparent,
          unfocusedIndicatorColor = Color.Transparent
        )
      )
    }
  }
}