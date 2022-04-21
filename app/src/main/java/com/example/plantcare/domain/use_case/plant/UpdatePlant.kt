package com.example.plantcare.domain.use_case.plant

import com.example.plantcare.domain.model.Plant
import com.example.plantcare.domain.repository.PlantRepository

class UpdatePlant(
  private val repository: PlantRepository
) {
  suspend operator fun invoke(plant: Plant) {
    return repository.updatePlant(plant = plant)
  }
}