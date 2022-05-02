package com.example.plantcare.presentation.add_edit_plant

sealed class AddEditPlantUiEvent {
  data class ShowError(val message: String): AddEditPlantUiEvent()
}
