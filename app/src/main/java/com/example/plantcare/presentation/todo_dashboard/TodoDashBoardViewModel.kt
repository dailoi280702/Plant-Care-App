package com.example.plantcare.presentation.todo_dashboard

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.plantcare.core.Constants.IMPORTANT
import com.example.plantcare.core.Constants.VERY_IMPORTANT
import com.example.plantcare.data.utils.DataState
import com.example.plantcare.domain.repository.TaskRepository
import com.example.plantcare.domain.use_case.plantTask.TodoUseCases
import com.example.plantcare.domain.utils.OrderType
import com.example.plantcare.domain.utils.TodoOrder
import com.example.plantcare.domain.utils.TodoTime
import com.example.plantcare.presentation.todo_dashboard.components.TodoDashboardState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TodoDashBoardViewModel @Inject constructor(
  todoUseCases: TodoUseCases
) : ViewModel() {
  
  private val _todoDashboardState = mutableStateOf(TodoDashboardState())
  val todoDashboardState: State<TodoDashboardState> = _todoDashboardState
  
  init {
    viewModelScope.launch {
      todoUseCases.getTodos(
        todoTimes = listOf(TodoTime.Today, TodoTime.UpComing, TodoTime.Overdue),
        todoOrder = TodoOrder.Default(OrderType.Descending)
      ).collectLatest {
        if (it is DataState.Success) {
          _todoDashboardState.value = todoDashboardState.value.copy(
            total = it.data.size,
            done = it.data.count { todo -> todo.done == true },
            important = it.data.count { todo -> todo.important == IMPORTANT },
            veryImportant = it.data.count { todo -> todo.important == VERY_IMPORTANT },
            overdue = it.data.count { todo -> todo.overDue == true },
            today = it.data.count() { todo -> todo.canTaskAddedToList(listOf(TodoTime.Today)) },
            upComing = it.data.count() { todo -> todo.canTaskAddedToList(listOf(TodoTime.UpComing)) },
            todayDone = it.data.count() { todo ->
              todo.done == true && todo.canTaskAddedToList(
                listOf(TodoTime.Today)
              )
            },
            upComingDone = it.data.count() { todo ->
              todo.done == true && todo.canTaskAddedToList(
                listOf(TodoTime.UpComing)
              )
            },
          )
        }
      }
    }
  }
}