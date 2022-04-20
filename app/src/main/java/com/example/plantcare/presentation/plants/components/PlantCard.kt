package com.example.plantcare.presentation.plants.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import java.sql.Date

@Composable
fun PlantCard(
  plantID: String,
  imageURL: String,
  name: String,
  dateAdded: Long,
  modifier: Modifier,
  onClick: () -> Unit
) {
  Column(
    modifier = modifier
  ) {
    Text(text = name)
    Text(text = Date(dateAdded).toString())
  }
}