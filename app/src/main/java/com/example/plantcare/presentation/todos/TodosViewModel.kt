package com.example.plantcare.presentation.todos

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.plantcare.data.utils.DataState
import com.example.plantcare.data.utils.DataState.*
import com.example.plantcare.domain.model.Todo
import com.example.plantcare.domain.use_case.plantTask.TaskUseCases
import com.example.plantcare.domain.utils.TodoOrder
import com.example.plantcare.domain.utils.TodoTime
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TodosViewModel @Inject constructor(
  private val taskUseCases: TaskUseCases
) : ViewModel() {
  
  private val _todosState = mutableStateOf(TodosState())
  val todosState: State<TodosState> = _todosState
  
  private val _dataState = mutableStateOf<DataState<*>>(Loading)
  val dataState: State<DataState<*>> = _dataState
  
  init {
    getTodos()
  }
  
  private fun getTodos(todoTimes: List<TodoTime>? = null, todoOrder: TodoOrder? = null) {
    viewModelScope.launch {
      taskUseCases.getTasks(
        todoTimes ?: todosState.value.todoTimes,
        todoOrder ?: todosState.value.todoOrder
      ).collectLatest {
        _dataState.value = it
        if (it is DataState.Success) {
          _todosState.value = todosState.value.copy(
            todos = it.data,
            todoTimes = todoTimes ?: todosState.value.todoTimes,
            todoOrder = todoOrder ?: todosState.value.todoOrder
          )
        }
      }
    }
  }
  
  fun onEvent(event: TodosEvent) {
    when (event) {
      is TodosEvent.ToggleFilterSection -> {
        _todosState.value = todosState.value.copy(
          filterSectionVisibility = !todosState.value.filterSectionVisibility
        )
      }
      is TodosEvent.UpdateTodoTimes -> {
        val todoTimes = todosState.value.todoTimes.toMutableList()
        
        if (todoTimes.count() <= 1 && event.value == todoTimes[0]) return
        
        if (todosState.value.todoTimes.contains(event.value)) {
          todoTimes.remove(event.value)
        } else {
          todoTimes.add(event.value)
        }
        
        getTodos(todoTimes = todoTimes)
      }
      is TodosEvent.UpdateTodoOrder -> {
        val todoOrder = todosState.value.todoOrder
        if (todoOrder.orderType == event.value.orderType && todoOrder::class.java == event.value::class.java) return
        
        getTodos(todoOrder = event.value)
      }
      is TodosEvent.RefreshTodos -> {
        getTodos()
      }
    }
  }
}