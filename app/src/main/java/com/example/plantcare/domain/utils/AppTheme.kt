package com.example.plantcare.domain.utils

sealed class AppTheme(val value: Int, val text: String) {
  object Auto : AppTheme(value = 0, text = "Auto")
  object Light : AppTheme(value = 1, text = "Light")
  object Dark : AppTheme(value = 2, text = "Dark")
  
  fun getNextTheme(): AppTheme {
    return when (this) {
      Auto -> Light
      Light -> Dark
      Dark -> Auto
    }
  }
  
  companion object{
    fun fromValue(value: Int?): AppTheme {
      return when (value) {
        1 -> Light
        2 -> Dark
        else -> Auto
      }
    }
  }
}
