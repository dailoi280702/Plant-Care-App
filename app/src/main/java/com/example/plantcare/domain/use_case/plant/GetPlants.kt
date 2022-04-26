package com.example.plantcare.domain.use_case.plant

import com.example.plantcare.domain.repository.PlantRepository

class GetPlants(
  private val repository: PlantRepository
) {
  operator fun invoke() = repository.getPlants()
}