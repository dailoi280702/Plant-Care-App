package com.example.plantcare.presentation.plant_detail

sealed class PlantDetailUiEvent {
  data class ShowError(val message: String): PlantDetailUiEvent()
  object NavigateBack : PlantDetailUiEvent()
}
