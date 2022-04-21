package com.example.plantcare.domain.use_case.plant

data class PlantUseCases(
  val getPlants: GetPlants,
  val getPlant: GetPlant,
  val addPlant: AddPlant,
  val deletePlant: DeletePlant,
  val updatePlant: UpdatePlant
)
