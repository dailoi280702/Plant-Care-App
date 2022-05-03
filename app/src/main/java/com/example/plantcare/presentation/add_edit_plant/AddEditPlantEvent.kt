package com.example.plantcare.presentation.add_edit_plant

import android.net.Uri

sealed class AddEditPlantEvent {
  object ToggleImageSection: AddEditPlantEvent()
  object ToggleInfoSection: AddEditPlantEvent()
  object ToggleTasksSection: AddEditPlantEvent()
  object ToggleSubFab: AddEditPlantEvent()
  object SavePlant: AddEditPlantEvent()
  data class EnterName(val value: String): AddEditPlantEvent()
  data class EnterDescription(val value: String): AddEditPlantEvent()
  data class ChangeImageUri(val uri: Uri?): AddEditPlantEvent()
}