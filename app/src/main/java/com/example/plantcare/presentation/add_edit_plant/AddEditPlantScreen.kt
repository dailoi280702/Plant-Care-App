package com.example.plantcare.presentation.add_edit_plant

import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import com.example.plantcare.R
import com.example.plantcare.presentation.main.MainViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import io.github.rosariopfernandes.firecoil.StorageReferenceFetcher
import io.github.rosariopfernandes.firecoil.StorageReferenceKeyer
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

@Composable
fun AddEditPlantScreen(
  navController: NavController,
  viewModel: AddEditPlantViewModel = hiltViewModel(),
  mainViewModel: MainViewModel
) {
  var selectedImage by remember {
    mutableStateOf<Uri?>(null)
  }
  val scope = rememberCoroutineScope()

  mainViewModel.setFloatingActionButton(
    icon = R.drawable.ic_save,
    contentDescription = "save icon"
  ) {
  }

  val launcher =
    rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri ->
      selectedImage = uri
    }

  val context = LocalContext.current
  val fireCoilLoader = ImageLoader.Builder(context)
    .components {
      add(StorageReferenceKeyer()) // this is necessary for local caching
      add(StorageReferenceFetcher.Factory())
    }
    .build()


  Column(
    modifier = Modifier
      .fillMaxSize(),
  ) {
    Text(text = "add edit plant screen ${viewModel.id.value}")
    Button(
      onClick = {
        launcher.launch("image/*")
      }
    ) {
      Text(text = "select img")
    }
    Button(
      onClick = {
        scope.launch {
          selectedImage?.let {
            try {
              Firebase.storage.reference.child("test_iamges/${Firebase.auth.currentUser?.uid}/${viewModel.id.value}").putFile(it).await()
            } catch (e: Exception) {
              Log.d("fuckit", e.message?:"errr")
            }
          }
        }
      }
    ) {
      Text(text = "save img")
    }
//    Image(
//      painter = rememberAsyncImagePainter(selectedImage),
//      contentDescription = null,
//      modifier = Modifier
//        .fillMaxWidth()
//        .clickable {
//          launcher.launch("image/*")
//        },
//      contentScale = ContentScale.Crop
//    )
//
    Image(
      painter = rememberAsyncImagePainter(
        model = Firebase.storage.reference.child("test_iamges/download.jpg"),
        imageLoader = fireCoilLoader
      ),
      contentDescription = null,
      modifier = Modifier.fillMaxWidth(),
      contentScale = ContentScale.Crop
    )
  }
}

