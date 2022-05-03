package com.example.plantcare.presentation.add_edit_plant

import android.net.Uri
import com.example.plantcare.domain.model.Plant

data class AddEditPlantState(
  val plant: Plant = Plant(),
  val expandedImage: Boolean = false,
  val expandedInfo: Boolean = true,
  val expandedTasks: Boolean = false,
  val imageUri: Uri? = null,
  val subFabVisibility: Boolean = false
)
