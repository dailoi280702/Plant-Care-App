package com.example.plantcare.domain.use_case.plant

import com.example.plantcare.domain.model.Plant
import com.example.plantcare.domain.repository.PlantRepository
import kotlinx.coroutines.flow.Flow

class GetPlants(
  private val repository: PlantRepository
) {
  operator fun invoke(): Flow<List<Plant>> {
    return repository.getPlants()
  }
}