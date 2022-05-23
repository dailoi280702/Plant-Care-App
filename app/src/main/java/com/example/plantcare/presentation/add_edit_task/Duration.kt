package com.example.plantcare.presentation.add_edit_task

sealed class Duration(val time: Int, val type: String) {
  data class Day(val value: Int): Duration(value, Duration.DAY)
  data class Week(val value: Int): Duration(value, Duration.WEEK)
  data class Month(val value: Int): Duration(value, Duration.MONTH)

  fun toInt(): Int {
    return when(this) {
      is Day -> {
        value
      }
      is Week -> {
        value * 7
      }
      is Month -> {
        value * 30
      }
    }
  }

  fun copy(time: Int?): Duration {
    time?.let {
      return when (this) {
        is Day -> Day(time)
        is Week -> Week(time)
        is Month -> Month(time)
      }
    }
    return Day(1)
  }

  fun copy(type: String): Duration {
    return when (type) {
      WEEK -> Week(time)
      MONTH -> Month(time)
      DAY -> Day(time)
      else -> Day(1)
    }
  }

  companion object {
    const val DAY: String = "day"
    const val WEEK: String = "week"
    const val MONTH: String = "Month"
  }
}
