package com.example.plantcare.domain.model

data class Plant(
  val id: String,
  val name: String,
  val description: String,
  val dateAdded: Long,
  val imageURL: String
)
