package com.example.plantcare.domain.utils

sealed class OrderType {
  object Ascending: OrderType()
  object Descending: OrderType()
}
