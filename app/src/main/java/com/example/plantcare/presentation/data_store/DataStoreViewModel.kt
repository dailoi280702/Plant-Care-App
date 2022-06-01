package com.example.plantcare.presentation.data_store

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.plantcare.core.Constants.APP_THEME
import com.example.plantcare.domain.utils.AppTheme
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DataStoreViewModel @Inject constructor(
  private val dataStore: DataStore<Preferences>
) : ViewModel() {
  private val forceDarkModeKey = intPreferencesKey(APP_THEME)
  
  private val _themeState = mutableStateOf<AppTheme>(AppTheme.Auto)
  val themeState: State<AppTheme> = _themeState
  
  init {
    request()
  }
  
  fun request() {
    viewModelScope.launch {
      dataStore.data.collectLatest {
        _themeState.value = AppTheme.fromValue(it[forceDarkModeKey])
      }
    }
  }
  
  fun switchMode() {
    val theme = themeState.value
    viewModelScope.launch {
      dataStore.edit {
        it[forceDarkModeKey] = theme.getNextTheme().value
      }
//      request()
    }
  }
}