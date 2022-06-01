package com.example.plantcare.presentation.todo

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.plantcare.data.utils.DataState
import com.example.plantcare.domain.model.Todo
import com.example.plantcare.domain.use_case.plantTask.TodoUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TodoViewModel @Inject constructor(
  private val todoUseCases: TodoUseCases
): ViewModel() {
  
  private val _todoState = mutableStateOf(TodoState())
  val todoState: State<TodoState> = _todoState
  
  private val _eventFLow = MutableSharedFlow<Boolean>()
  val eventFlow = _eventFLow.asSharedFlow()
  
  private fun deleteTodo(todo: Todo) {
    viewModelScope.launch {
      todoUseCases.deleteTodo(todo.todoId!!).collectLatest {
        if (it is DataState.Success) {
          _todoState.value = todoState.value.copy(
            deletedTodo = todo
          )
          _eventFLow.emit(true)
        }
      }
    }
  }
  
  private  fun restoreTodo() {
    todoState.value.deletedTodo?.let { todo ->
      viewModelScope.launch {
        todoUseCases.addTodo(todo).collectLatest {
          if (it is DataState.Success) {
            _todoState.value = todoState.value.copy(
              deletedTodo = null
            )
          }
        }
      }
    }
  }
  
  private fun markTodoAsDone(todo: Todo) {
    viewModelScope.launch {
      todoUseCases.markTodoAsDone(todo)
    }
  }
  
  fun onEvent(event: TodoEvent) {
    when (event) {
      is TodoEvent.DeleteTodo -> {
        deleteTodo(event.value)
      }
      is TodoEvent.UpdateExpandedCard -> {
        val expandedCard = if (todoState.value.expandedCard == event.value) null else event.value
        _todoState.value = todoState.value.copy(
          expandedCard = expandedCard
        )
      }
      is TodoEvent.RestoreTodo -> {
        restoreTodo()
      }
      is TodoEvent.MarkTodoAsDone -> {
        markTodoAsDone(event.value)
      }
    }
  }
}