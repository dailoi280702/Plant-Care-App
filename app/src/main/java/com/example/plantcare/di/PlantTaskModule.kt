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
  ) = TaskUseCases(
    addTask = AddTask(repository = repository),
    getTask = GetTask(repository = repository),
    getTasks = GetTasks(repository = repository),
    getTasksByPlantId = GetTasksByPlantId(repository = repository),
    updateTask = UpdateTask(repository = repository),
    deleteTask = DeleteTask(repository = repository)
  )
}