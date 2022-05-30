package com.example.plantcare.domain.utils

sealed class OrderType (val text: String) {
  object Ascending: OrderType("Ascending")
  object Descending: OrderType("Descending")
}
