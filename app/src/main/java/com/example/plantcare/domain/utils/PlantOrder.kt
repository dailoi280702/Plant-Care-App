package com.example.plantcare.domain.utils

sealed class PlantOrder(val orderType: OrderType, val orderName: String) {
  class Name(orderType: OrderType) : PlantOrder(orderType, "name")
  class Description(orderType: OrderType) : PlantOrder(orderType, "description")
  class DateAdded(orderType: OrderType) : PlantOrder(orderType, "dateAdded")

  fun copy(orderType: OrderType): PlantOrder {
    return when(this) {
      is Name -> Name(orderType = orderType)
      is Description -> Description(orderType = orderType)
      is DateAdded -> DateAdded(orderType = orderType)
    }
  }
}
