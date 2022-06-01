package com.example.plantcare.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.migrations.SharedPreferencesMigration
import androidx.datastore.preferences.SharedPreferencesMigration
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStoreFile
import com.example.plantcare.core.Constants.USER_PREFERENCES
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DataStoreModule {
  
  @Singleton
  @Provides
  fun providePreferencesDataStore(@ApplicationContext appContext: Context): DataStore<Preferences> {
    return PreferenceDataStoreFactory.create(
      corruptionHandler = ReplaceFileCorruptionHandler(
        produceNewData = { emptyPreferences() }
      ),
      migrations = listOf(SharedPreferencesMigration(appContext,USER_PREFERENCES)),
      scope = CoroutineScope(Dispatchers.IO + SupervisorJob()),
      produceFile = { appContext.preferencesDataStoreFile(USER_PREFERENCES) }
    )
  }
}
