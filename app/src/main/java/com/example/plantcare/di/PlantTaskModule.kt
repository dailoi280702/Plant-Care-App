package com.example.plantcare.di

import com.example.plantcare.data.repository.TaskRepositoryImpl
import com.example.plantcare.domain.repository.TaskRepository
import com.example.plantcare.domain.use_case.plantTask.*
import com.google.firebase.firestore.DocumentReference
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PlantTaskModule {

  @Singleton
  @Provides
  fun provideTaskRepository(
    documentRef: DocumentReference
  ): TaskRepository = TaskRepositoryImpl(documentRef = documentRef)

  @Singleton
  @Provides
  fun provideTaskUseCases(
    repository: TaskRepository
  ) = TodoUseCases(
    addTodo = AddTodo(repository = repository),
    getTodo = GetTodo(repository = repository),
    getTodos = GetTodos(repository = repository),
    getTodosByPlantId = GetTodosByPlantId(repository = repository),
    updateTodo = UpdateTodo(repository = repository),
    deleteTodo = DeleteTodo(repository = repository),
    markTodoAsDone = MarkTodoAsDone(repository = repository)
  )
}