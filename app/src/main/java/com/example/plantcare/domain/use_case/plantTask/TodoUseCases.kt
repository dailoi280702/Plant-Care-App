package com.example.plantcare.domain.use_case.plantTask

data class TodoUseCases(
  val addTodo: AddTodo,
  val getTodo: GetTodo,
  val getTodos: GetTodos,
  val getTodosByPlantId: GetTodosByPlantId,
  val updateTodo: UpdateTodo,
  val deleteTodo: DeleteTodo,
  val markTodoAsDone: MarkTodoAsDone
)