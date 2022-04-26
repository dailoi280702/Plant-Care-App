package com.example.plantcare.domain.model

data class Plant(
  var id: String? = null,
  val name: String? = null,
  val description: String? = null,
  val dateAdded: Long? = null,
  val imageURL: String? = null
)
