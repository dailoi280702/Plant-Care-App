package com.example.plantcare.presentation.plant_detail.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InfoSection(
  name: String,
  description: String,
  onNameChange: (String) -> Unit,
  onDescriptionChange: (String) -> Unit
) {
  
  val focusManager = LocalFocusManager.current
  
  Column(
    Modifier
      .fillMaxWidth()
      .padding(horizontal = 8.dp)
  ) {
    TextField(
      modifier = Modifier
        .offset(y = (-16).dp)
        .fillMaxWidth(),
      value = name,
      onValueChange = {
        onNameChange(it)
      },
      singleLine = true,
      placeholder = {
        Text(
          text = "Name*",
          color = MaterialTheme.colorScheme.onSurface,
          style = MaterialTheme.typography.titleMedium
        )
      },
      textStyle = MaterialTheme.typography.titleMedium,
      shape = RectangleShape,
      colors = TextFieldDefaults.textFieldColors(
        cursorColor = MaterialTheme.colorScheme.onSurface,
        disabledLabelColor = MaterialTheme.colorScheme.secondary,
        focusedIndicatorColor = Color.Transparent,
        unfocusedIndicatorColor = Color.Transparent,
        containerColor = MaterialTheme.colorScheme.background
      ),
      keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Words, imeAction = ImeAction.Done),
      keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() })
    )
    Divider(
      color = MaterialTheme.colorScheme.surfaceVariant,
      thickness = 1.dp,
      modifier = Modifier
        .offset(y = (-24).dp)
        .padding(horizontal = 16.dp)
    )
    TextField(
      modifier = Modifier
        .offset(y = (-24).dp)
        .fillMaxWidth(),
      value = description,
      onValueChange = {
        onDescriptionChange(it)
      },
      singleLine = false,
      placeholder = {
        Text(
          text = "Description",
          color = MaterialTheme.colorScheme.onSurface,
          style = MaterialTheme.typography.bodyMedium
        )
      },
      textStyle = MaterialTheme.typography.bodyMedium,
      shape = RectangleShape,
      colors = TextFieldDefaults.textFieldColors(
        cursorColor = MaterialTheme.colorScheme.onSurface,
        disabledLabelColor = MaterialTheme.colorScheme.secondary,
        focusedIndicatorColor = Color.Transparent,
        unfocusedIndicatorColor = Color.Transparent,
        containerColor = MaterialTheme.colorScheme.background
      ),
      keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Sentences),
      keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() })
    )
  }
}
