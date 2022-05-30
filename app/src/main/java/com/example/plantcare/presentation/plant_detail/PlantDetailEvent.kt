package com.example.plantcare.presentation.plant_detail

import android.net.Uri
import com.example.plantcare.domain.model.Todo

sealed class PlantDetailEvent {
  object ToggleImageSection: PlantDetailEvent()
  object ToggleInfoSection: PlantDetailEvent()
  object ToggleTasksSection: PlantDetailEvent()
  object ToggleSubFab: PlantDetailEvent()
  object SavePlant: PlantDetailEvent()
  object DeletePlant: PlantDetailEvent()
  data class EnterName(val value: String): PlantDetailEvent()
  data class EnterDescription(val value: String): PlantDetailEvent()
  data class ChangeImageUri(val uri: Uri?): PlantDetailEvent()
}