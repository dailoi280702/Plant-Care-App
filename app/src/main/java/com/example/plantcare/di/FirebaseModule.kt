package com.example.plantcare.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FirebaseModule {

  @Provides
  @Singleton
  fun provideAuth() = FirebaseAuth.getInstance()

  @Provides
  @Singleton
  fun provideFireStore(): FirebaseFirestore = FirebaseFirestore.getInstance()

  @Provides
  @Singleton
  fun provideFirebaseStorage(): StorageReference = Firebase.storage.reference

  @Provides
  @Singleton
  fun provideUserID(auth: FirebaseAuth): String? = auth.currentUser?.uid
}
