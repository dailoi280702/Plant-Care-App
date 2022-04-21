package com.example.plantcare.domain.use_case.plant

import com.example.plantcare.domain.repository.PlantRepository

class DeletePlant(
  private val repository: PlantRepository
) {
  suspend operator fun invoke(id: String) {
    return repository.deletePlant(id = id)
  }
}