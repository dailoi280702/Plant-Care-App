package com.example.plantcare.domain.model

import com.example.plantcare.core.Utils.Companion.compareTwoDates
import com.example.plantcare.domain.use_case.plant.GetPlantName
import com.example.plantcare.domain.utils.PlantTaskStatus
import com.example.plantcare.domain.utils.TodoTime
import com.google.firebase.Timestamp
import java.util.*

data class Todo(
  var todoId: String? = "",
  var plantId: String? = "",
  var plantName: String? = "",
  val title: String? = "",
  var dueDay: Timestamp? = null,
  val duration: Int? = 0,
  val important: Int? = 0,
  val repeatable: Boolean = false,
  var done: Boolean = false,
  var overDue: Boolean = false
) {
  
  fun updateTask(): PlantTaskStatus {
    val now = Calendar.getInstance()
    val dueDate = Calendar.getInstance()
    dueDate.time = dueDay!!.toDate()
    
    return if (compareTwoDates(dueDay!!.toDate(), now.time) < 0) {
      if (done) {
        if (repeatable) {
          while (compareTwoDates(dueDate.time, now.time) < 0) {
            dueDate.add(Calendar.DATE, duration!!)
          }
          dueDay = Timestamp(dueDate.time)
          overDue = false
          done = false
          PlantTaskStatus.Updated
        } else {
          PlantTaskStatus.MustBeDeleted
        }
      } else {
        if (!overDue) {
          overDue = true
          PlantTaskStatus.Updated
        } else {
          PlantTaskStatus.UpToDate
        }
      }
    } else {
      PlantTaskStatus.UpToDate
    }
  }
  
  fun canTaskAddedToList(todoTimes: List<TodoTime>): Boolean {
    val now = Calendar.getInstance()
    val compareResult = compareTwoDates(dueDay!!.toDate(), now.time)
    
    if (compareResult < 0L && todoTimes.contains(TodoTime.Overdue))
      return true
    
    if (compareResult > 0L && todoTimes.contains(TodoTime.UpComing))
      return true
  
    if (compareResult == 0L && todoTimes.contains(TodoTime.Today))
      return true
    
    return false
  }
}
