package com.example.plantcare.domain.use_case.plantTask

data class TaskUseCases(
  val addTask: AddTask,
  val getTask: GetTask,
  val getTasks: GetTasks,
  val getTasksByPlantId: GetTasksByPlantId,
  val updateTask: UpdateTask,
  val deleteTask: DeleteTask
)