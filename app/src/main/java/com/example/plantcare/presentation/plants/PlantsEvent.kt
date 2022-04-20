package com.example.plantcare.presentation.plants

import com.example.plantcare.domain.utils.PlantOrder

sealed class PlantsEvent {
  data class Order(val plantOrder: PlantOrder): PlantsEvent()
  data class Search(val searchValue: String): PlantsEvent()
  object ToggleOrderSession: PlantsEvent()
}
