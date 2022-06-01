package com.example.plantcare.core

import android.annotation.SuppressLint
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.google.firebase.Timestamp
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.math.abs


class Utils {
  companion object {

    @SuppressLint("SimpleDateFormat")
    fun convertLongToTime(time: Long): String {
      val date = Date(time)
      val format = SimpleDateFormat("yyyy.MM.dd - HH:mm")
      return format.format(date)
    }

    fun timeStampToString(timestamp: Timestamp): String {
      val dayLeft = compareTwoDates(timestamp.toDate(), Calendar.getInstance().time)
      
      if (dayLeft > 1L ) return "$dayLeft days left"
      if (dayLeft < -1L) return  "Overdue $dayLeft days"
      if (dayLeft == 1L) return  "Tomorrow"
      if (dayLeft == -1L) return  "Overdue $dayLeft day"
      return "Today"
    }
  
    @Composable
    fun timeStampToColor(timestamp: Timestamp): Color {
      val dayLeft = compareTwoDates(timestamp.toDate(), Calendar.getInstance().time)
    
      if (dayLeft >= 1L ) return MaterialTheme.colorScheme.onSurfaceVariant
      if (dayLeft <= -1L) return MaterialTheme.colorScheme.error
      return MaterialTheme.colorScheme.primary
    }

    fun calculateDifferenceInDays(date: Date): Long {
      val now = Calendar.getInstance()
      return abs(TimeUnit.MILLISECONDS.toDays(date.time) - TimeUnit.MILLISECONDS.toDays(now.time.time))
    }

    fun compareTwoDates(d1: Date, d2: Date): Long {
      return TimeUnit.MILLISECONDS.toDays(d1.time) - TimeUnit.MILLISECONDS.toDays(d2.time)
    }

  }
}