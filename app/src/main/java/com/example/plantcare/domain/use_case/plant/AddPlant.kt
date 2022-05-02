package com.example.plantcare.domain.use_case.plant

import android.net.Uri
import com.example.plantcare.domain.model.Plant
import com.example.plantcare.domain.repository.PlantRepository

class AddPlant(
  private val repository: PlantRepository
) {
  suspend operator fun invoke(plant: Plant, uri: Uri?) = repository.addPlant(plant = plant, uri = uri)
}