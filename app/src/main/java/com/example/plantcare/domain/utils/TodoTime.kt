package com.example.plantcare.domain.utils

sealed class TodoTime (val text: String) {
  object Today: TodoTime("Today")
  object UpComing: TodoTime("UpComing")
  object Overdue: TodoTime("Overdue")
}
