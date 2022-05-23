package com.example.plantcare.domain.utils

sealed class PlantTaskStatus {
  object MustBeDeleted: PlantTaskStatus()
  object Updated: PlantTaskStatus()
  object UpToDate: PlantTaskStatus()
}
