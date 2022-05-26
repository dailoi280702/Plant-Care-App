package com.example.plantcare.data.repository;

import com.example.plantcare.data.utils.DataState.*
import com.example.plantcare.domain.model.PlantTask
import com.example.plantcare.domain.repository.TaskRepository
import com.example.plantcare.domain.utils.PlantTaskStatus
import com.example.plantcare.domain.utils.TaskOrder
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
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

    val taskCollection = documentRef.collection("tasks")
    val snapshotListener = taskCollection.addSnapshotListener { snapshot, e ->
      if (e != null) {
        trySend(Error(e.message.toString()))
      }

      val data = if (snapshot != null && e == null) {
        try {
          val tasks = mutableListOf<PlantTask>()
          snapshot.onEach {
            val task = it.toObject(PlantTask::class.java)
            when (task.updateTask()) {
              is PlantTaskStatus.MustBeDeleted -> {
                documentRef.collection("tasks").document(task.taskId!!).delete()
              }
              is PlantTaskStatus.Updated -> {
                documentRef.collection("tasks").document(task.taskId!!)
                  .set(task, SetOptions.merge())
                tasks.add(task)
              }
              is PlantTaskStatus.UpToDate -> {
                tasks.add(task)
              }
            }
          }
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

    val taskCollection = documentRef.collection("tasks").whereEqualTo("plantId", plantId)
    val snapshotListener = taskCollection.addSnapshotListener { snapshot, e ->
      if (e != null) {
        trySend(Error(e.message.toString()))
      }

      val data = if (snapshot != null && e == null) {
        try {
          val tasks = mutableListOf<PlantTask>()
          snapshot.onEach {
            val task = it.toObject(PlantTask::class.java)
            when (task.updateTask()) {
              is PlantTaskStatus.MustBeDeleted -> {
                documentRef.collection("tasks").document(task.taskId!!).delete()
              }
              is PlantTaskStatus.Updated -> {
                documentRef.collection("tasks").document(task.taskId!!)
                  .set(task, SetOptions.merge())
                tasks.add(task)
              }
              is PlantTaskStatus.UpToDate -> {
                tasks.add(task)
              }
            }
          }
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
      emit(Error("Please enter title"))
      return@flow
    }

    try {
      emit(Loading)
      val id = documentRef.collection("tasks").add(task).await().id
      val newTask = task.copy(
        taskId = id
      )
      documentRef.collection("tasks").document(id).update("taskId", id).await()
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

//    try {
//      emit(Loading)
//      val id = task.taskId!!
//      documentRef.collection("tasks").document(id).set(task, SetOptions.merge()).await()
//      emit(Success(task))
//    } catch (e: Exception) {
//      emit(Error(e.message ?: e.toString()))
//    }
    
    try {
      FirebaseFirestore.getInstance().runBatch { batch ->
        val id = task.taskId!!
        batch.set(documentRef.collection("tasks").document(id), task)
      }.await()
      emit(Success(task))
    } catch (e: Exception) {
      emit(Error(e.message ?: e.toString()))
    }
  }
}
