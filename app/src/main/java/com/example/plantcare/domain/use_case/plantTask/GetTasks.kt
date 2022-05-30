package com.example.plantcare.domain.use_case.plantTask

import com.example.plantcare.data.utils.DataState.Success
import com.example.plantcare.domain.repository.TaskRepository
import com.example.plantcare.domain.utils.OrderType
import com.example.plantcare.domain.utils.TodoOrder
import com.example.plantcare.domain.utils.TodoTime
import kotlinx.coroutines.flow.map

class GetTasks(
  private val repository: TaskRepository
) {
  operator fun invoke(todoTimes: List<TodoTime>, todoOrder: TodoOrder) = repository.getTasks().map { dataState ->
    when (dataState) {
      is Success -> {
        var data = dataState.data.filter { it.canTaskAddedToList(todoTimes) }
        when (todoOrder.orderType) {
          is OrderType.Ascending -> {
            data = when (todoOrder) {
              is TodoOrder.Default -> {
                data.sortedBy { it.dueDay }.sortedByDescending { it.important }.sortedBy { it.done }
              }
              is TodoOrder.Title -> {
                data.sortedBy {it.title}
              }
              is TodoOrder.DueDate -> {
                data.sortedBy {it.dueDay}
              }
              is TodoOrder.Importance -> {
                data.sortedBy {it.important}
              }
            }
          }
          is OrderType.Descending -> {
            data = when (todoOrder) {
              is TodoOrder.Default -> {
                data.sortedBy { it.dueDay }.sortedByDescending { it.important }.sortedBy { it.done }
              }
              is TodoOrder.Title -> {
                data.sortedByDescending {it.title}
              }
              is TodoOrder.DueDate -> {
                data.sortedByDescending {it.dueDay}
              }
              is TodoOrder.Importance -> {
                data.sortedByDescending {it.important}
              }
            }
          }
        }
        Success(data.sortedBy { it.done })
      }
      else -> {
        dataState
      }
    }
  }
}