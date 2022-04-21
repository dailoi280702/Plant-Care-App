package com.example.plantcare.data.utils

sealed class DataState<T> {
  class Loading<T> : DataState<T>()
  data class Success<T>(val data: T) : DataState<T>()
  data class Failed<T>(val message: String) : DataState<T>()

  companion object {
    fun <T> loading() = Loading<T>()
    fun <T> success(data: T) = Success(data)
    fun <T> failed(message: String) = Failed<T>(message)
  }
}
