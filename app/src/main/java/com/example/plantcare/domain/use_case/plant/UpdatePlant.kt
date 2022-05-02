package com.example.plantcare.domain.use_case.plant

import android.net.Uri
import com.example.plantcare.domain.model.Plant
import com.example.plantcare.domain.repository.PlantRepository

class UpdatePlant(
  private val repository: PlantRepository
) {
  suspend operator fun invoke(plant: Plant, uri: Uri?) = repository.updatePlant(plant = plant, uri = uri)
}