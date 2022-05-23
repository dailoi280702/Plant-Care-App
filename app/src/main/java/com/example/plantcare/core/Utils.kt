package com.example.plantcare.core

import android.annotation.SuppressLint
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

    @SuppressLint("SimpleDateFormat")
    fun timeStampToDateString(timestamp: Timestamp): String {
      val date = timestamp.toDate()
      val format = SimpleDateFormat("yyyy.MM.dd - HH:mm")
      return format.format(date)
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