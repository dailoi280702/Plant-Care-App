package com.example.plantcare.data.repository

import com.example.plantcare.core.Constants.TODOS
import com.example.plantcare.core.Constants.TODO_ID
import com.example.plantcare.data.utils.DataState.*
import com.example.plantcare.domain.model.Todo
import com.example.plantcare.domain.repository.TaskRepository
import com.example.plantcare.domain.utils.PlantTaskStatus
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

  override fun getTasks() = callbackFlow {

    val taskCollection = documentRef.collection(TODOS)
    val snapshotListener = taskCollection.addSnapshotListener { snapshot, e ->
      if (e != null) {
        trySend(Error(e.message.toString()))
      }

      val data = if (snapshot != null && e == null) {
        try {
          val tasks = mutableListOf<Todo>()
          snapshot.onEach {
            val task = it.toObject(Todo::class.java)
            when (task.updateTask()) {
              is PlantTaskStatus.MustBeDeleted -> {
                documentRef.collection(TODOS).document(task.todoId!!).delete()
              }
              is PlantTaskStatus.Updated -> {
                documentRef.collection(TODOS).document(task.todoId!!)
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

    val taskCollection = documentRef.collection(TODOS).whereEqualTo("plantId", plantId)
    val snapshotListener = taskCollection.addSnapshotListener { snapshot, e ->
      if (e != null) {
        trySend(Error(e.message.toString()))
      }

      val data = if (snapshot != null && e == null) {
        try {
          val tasks = mutableListOf<Todo>()
          snapshot.onEach {
            val task = it.toObject(Todo::class.java)
            when (task.updateTask()) {
              is PlantTaskStatus.MustBeDeleted -> {
                documentRef.collection(TODOS).document(task.todoId!!).delete()
              }
              is PlantTaskStatus.Updated -> {
                documentRef.collection(TODOS).document(task.todoId!!)
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
      val snapshot = documentRef.collection(TODOS).document(id).get().await()
      val task = snapshot.toObject(Todo::class.java)
      emit(Success(task))
    } catch (e: Exception) {
      emit(Error(e.message.toString()))
    }
  }

  override suspend fun addTask(task: Todo) = flow {

    if (task.title.isNullOrEmpty() || task.title.isNullOrBlank()) {
      emit(Error("Please enter title"))
      return@flow
    }

    try {
      emit(Loading)
      val id = documentRef.collection(TODOS).add(task).await().id
      val newTask = task.copy(
        todoId = id
      )
      documentRef.collection(TODOS).document(id).update(TODO_ID, id).await()
      emit(Success(newTask))
    } catch (e: Exception) {
      emit(Error(e.message ?: e.toString()))
    }
  }

  override suspend fun deleteTask(id: String) = flow {

    try {
      emit(Loading)
      documentRef.collection(TODOS).document(id).delete().await()
      emit(Success(null))
    } catch (e: Exception) {
      emit(Error(e.message ?: e.toString()))
    }
  }

  override suspend fun updateTask(task: Todo) = flow {
    if (task.title.isNullOrEmpty() || task.title.isNullOrBlank()) {
      emit(Error("title cannot be empty"))
      return@flow
    }

//    try {
//      emit(Loading)
//      val id = task.taskId!!
//      documentRef.collection(TODOS).document(id).set(task, SetOptions.merge()).await()
//      emit(Success(task))
//    } catch (e: Exception) {
//      emit(Error(e.message ?: e.toString()))
//    }
    
    try {
      FirebaseFirestore.getInstance().runBatch { batch ->
        val id = task.todoId!!
        batch.set(documentRef.collection(TODOS).document(id), task)
      }.await()
      emit(Success(task))
    } catch (e: Exception) {
      emit(Error(e.message ?: e.toString()))
    }
  }
  
  override suspend fun markTodoAsDone(todo: Todo) {
    todo.todoId?.let {
      documentRef.collection(TODOS).document(it).update("done", !todo.done)
    }
  }
}
