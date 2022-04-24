package com.example.plantcare.presentation.plants

import com.example.plantcare.domain.utils.PlantOrder

sealed class PlantsScreenEvent {
  data class Order(val plantOrder: PlantOrder) : PlantsScreenEvent()
  object ToggleOrderSection : PlantsScreenEvent()
}
