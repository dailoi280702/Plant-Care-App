package com.example.plantcare.presentation.todo_dashboard.components

data class TodoDashboardState(
  val total: Int = 0,
  val done: Int = 0,
  val today: Int = 0,
  val upComing: Int = 0,
  val todayDone: Int = 0,
  val upComingDone: Int = 0,
  val important: Int = 0,
  val veryImportant: Int = 0,
  val overdue: Int = 0,
)
