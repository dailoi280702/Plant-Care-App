package com.example.plantcare.presentation.add_edit_plant

import android.net.Uri
import com.example.plantcare.domain.model.PlantTask

sealed class AddEditPlantEvent {
  object ToggleImageSection: AddEditPlantEvent()
  object ToggleInfoSection: AddEditPlantEvent()
  object ToggleTasksSection: AddEditPlantEvent()
  object ToggleSubFab: AddEditPlantEvent()
  object ToggleTaskDialog: AddEditPlantEvent()
  object SavePlant: AddEditPlantEvent()
  object DeletePlant: AddEditPlantEvent()
  data class EnterName(val value: String): AddEditPlantEvent()
  data class EnterDescription(val value: String): AddEditPlantEvent()
  data class ChangeImageUri(val uri: Uri?): AddEditPlantEvent()
  data class UpdateCurrentPlantTask(val value: PlantTask?): AddEditPlantEvent()
}