package com.example.plantcare.domain.use_case.plant

import com.example.plantcare.domain.repository.PlantRepository
import com.example.plantcare.domain.utils.PlantOrder

class GetPlants(
  private val repository: PlantRepository
) {
  operator fun invoke(plantOrder: PlantOrder? = null, limit: Long? = null) =
    repository.getPlants(plantOrder = plantOrder, limit = limit)
}