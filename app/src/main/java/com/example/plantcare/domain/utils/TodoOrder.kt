package com.example.plantcare.domain.utils

sealed class TodoOrder(val orderType: OrderType, val text: String) {
  class Default(orderType: OrderType) : TodoOrder(orderType, "Default")
  class Title(orderType: OrderType) : TodoOrder(orderType, "Title")
  class Importance(orderType: OrderType) : TodoOrder(orderType, "Importance")
  class DueDate(orderType: OrderType) : TodoOrder(orderType, "Due date")

  fun copy(orderType: OrderType):TodoOrder {
    return when(this) {
      is Title -> Title(orderType = orderType)
      is Importance -> Importance(orderType = orderType)
      is DueDate -> DueDate(orderType = orderType)
      else -> this
    }
  }
}
