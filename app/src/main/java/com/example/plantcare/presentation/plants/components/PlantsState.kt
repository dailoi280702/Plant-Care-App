package com.example.plantcare.presentation.plants.components

import com.example.plantcare.domain.model.Plant
import com.example.plantcare.domain.utils.OrderType
import com.example.plantcare.domain.utils.PlantOrder

data class PlantsState(
  val plants: List<Plant> = emptyList(),
  val plantsOrder: PlantOrder = PlantOrder.DateAdded(OrderType.Descending),
  val isOrderSessionVisible: Boolean = false,
  val searchString: String? = null
)