package com.example.plantcare.domain.use_case.plant

import com.example.plantcare.domain.repository.PlantRepository

class GetPlant(
  private val repository: PlantRepository
) {
  suspend operator fun invoke(id: String) = repository.getPlant(id = id)
}