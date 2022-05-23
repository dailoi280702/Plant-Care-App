package com.example.plantcare.domain.utils

sealed class TaskOrder(val orderType: OrderType, val orderName: String) {
  class Name(orderType: OrderType) : TaskOrder(orderType, "name")
  class Important(orderType: OrderType) : TaskOrder(orderType, "important")
  class Time(orderType: OrderType) : TaskOrder(orderType, "time")

  fun copy(orderType: OrderType):TaskOrder {
    return when(this) {
      is Name -> Name(orderType = orderType)
      is Important -> Important(orderType = orderType)
      is Time -> Time(orderType = orderType)
    }
  }
}
