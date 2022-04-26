package com.example.plantcare.domain.utils

sealed class PlantOrder(val orderType: OrderType) {
  class Name(orderType: OrderType) : PlantOrder(orderType)
  class Description(orderType: OrderType) : PlantOrder(orderType)
  class DateAdded(orderType: OrderType) : PlantOrder(orderType)

  fun copy(orderType: OrderType): PlantOrder {
    return when(this) {
      is Name -> Name(orderType = orderType)
      is Description -> Description(orderType = orderType)
      is DateAdded -> DateAdded(orderType = orderType)
    }
  }
}
