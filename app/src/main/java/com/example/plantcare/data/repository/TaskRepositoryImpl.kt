package com.example.plantcare.data.repository;

import android.util.Log
import com.example.plantcare.data.utils.DataState.*
import com.example.plantcare.domain.model.PlantTask
import com.example.plantcare.domain.repository.TaskRepository
import com.example.plantcare.domain.utils.TaskOrder
import com.google.firebase.firestore.DocumentReference
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Singleton

@Singleton
class TaskRepositoryImpl(
  private val documentRef: DocumentReference
) : TaskRepository {

  override fun getTasks(taskOrder: TaskOrder?) = callbackFlow {
    val snapshotListener = documentRef.collection("tasks").addSnapshotListener { snapshot, e ->
      val data = if (snapshot != null) {
        try {
          val tasks = snapshot.toObjects(PlantTask::class.java)
          Success(tasks)
        } catch (e: Exception) {
          Error(e.message.toString())
        }
      } else {
        Error(e?.message.toString())
      }
      trySend(data).isSuccess
    }

    awaitClose {
      snapshotListener.remove()
    }
  }

  override fun getTasksByPlantID(plantId: String) = callbackFlow {

    val snapshotListener = documentRef.collection("tasks").whereEqualTo("plantId", plantId)
      .addSnapshotListener { snapshot, e ->
        val data = if (snapshot != null) {
          try {
            val plants = snapshot.toObjects(PlantTask::class.java)
            Success(plants)
          } catch (e: Exception) {
            Error(e.message.toString())
          }
        } else {
          Error(e?.message.toString())
        }
        trySend(data).isSuccess
      }

    awaitClose {
      snapshotListener.remove()
    }
  }

  override suspend fun getTask(id: String) = flow {

    try {
      emit(Loading)
      val snapshot = documentRef.collection("tasks").document(id).get().await()
      val task = snapshot.toObject(PlantTask::class.java)
      emit(Success(task))
    } catch (e: Exception) {
      emit(Error(e.message.toString()))
    }
  }

  override suspend fun addTask(task: PlantTask) = flow {

    if (task.title.isNullOrEmpty() || task.title.isNullOrBlank()) {
      Log.d("asd123xxx", "???")
      emit(Error("Please enter title"))
      return@flow
    }
    if (task.description.isNullOrEmpty() || task.description.isNullOrBlank()) {
      emit(Error("Please enter description"))
      return@flow
    }

    try {
      emit(Loading)
      val id = documentRef.collection("tasks").add(task).await().id
      val newTask = task.copy(
          taskId = id,
          timestamp = System.currentTimeMillis()
        )
      documentRef.collection("tasks").document(id).set(
        newTask
      ).await()
      emit(Success(newTask))
    } catch (e: Exception) {
      emit(Error(e.message ?: e.toString()))
    }
  }

  override suspend fun deleteTask(id: String) = flow {

    try {
      emit(Loading)
      documentRef.collection("tasks").document(id).delete().await()
      emit(Success(null))
    } catch (e: Exception) {
      emit(Error(e.message ?: e.toString()))
    }
  }

  override suspend fun updateTask(task: PlantTask) = flow {

    if (task.title.isNullOrEmpty() || task.title.isNullOrBlank()) {
      emit(Error("title cannot be empty"))
      return@flow
    }

    try {
      emit(Loading)
      documentRef.collection("tasks").document(task.taskId!!).set(
        task
      )
      emit(Success(null))
    } catch (e: Exception) {
      emit(Error(e.message ?: e.toString()))
    }
  }
}
