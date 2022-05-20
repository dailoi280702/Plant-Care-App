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
import java.util.*
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
      channel.close()
    }
  }

  override fun getTasksByPlantID(plantId: String) = callbackFlow {

    val taskCollection = documentRef.collection("tasks").orderBy("done")
//    val taskCollection = documentRef.collection("tasks").whereEqualTo("plantId", plantId)
    val snapshotListener = taskCollection.addSnapshotListener { snapshot, e ->
      if (e != null) {
        trySend(Error(e.message.toString()))
      }

      val data = if (snapshot != null && e == null) {
        try {
          val now = Calendar.getInstance().time
          val tasks = mutableListOf<PlantTask>()
          snapshot.onEach {
            val task = it.toObject(PlantTask::class.java)
            if (task.plantId == plantId) {
              val dueDate = task.dueDay!!.toDate()
              if (com.example.plantcare.core.Utils.compareTwoDates(dueDate, now) < 0) {
                task.overDue = true
                if (!task.repeatable && task.done) {
                  documentRef.collection("tasks").document(task.taskId!!).delete()
                }
                else {
                  tasks.add(task)
                }
              } else {
                task.overDue = false
                tasks.add(task)
              }
            }
          }
//          val plants = snapshot.toObjects(PlantTask::class.java)
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
      Log.d("asd123xxx", "???")
      emit(Error("Please enter title"))
      return@flow
    }

    try {
      emit(Loading)
      val id = documentRef.collection("tasks").add(task).await().id
      val newTask = task.copy(
        taskId = id
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
