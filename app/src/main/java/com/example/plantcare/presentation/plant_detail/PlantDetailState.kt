package com.example.plantcare.presentation.plant_detail

import android.net.Uri
import com.example.plantcare.domain.model.Plant

data class PlantDetailState(
  val plant: Plant = Plant(),
  val expandedImage: Boolean = false,
  val expandedInfo: Boolean = true,
  val expandedTasks: Boolean = false,
  val imageUri: Uri? = null,
  val subFabVisibility: Boolean = false,
  val confirmDeletionDialogVisibility: Boolean = false
)
