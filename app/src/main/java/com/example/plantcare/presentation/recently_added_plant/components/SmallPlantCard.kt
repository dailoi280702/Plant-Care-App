package com.example.plantcare.presentation.recently_added_plant.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import com.example.plantcare.domain.model.Plant

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SmallPlantCard(
  plant: Plant,
  onClick:() -> Unit
) {

  val painter = rememberAsyncImagePainter(model = plant.imageURL)
  val painterState = painter.state

  Card(
    modifier = Modifier
      .fillMaxHeight()
      .padding(4.dp)
      .width(120.dp),
    elevation = CardDefaults.elevatedCardElevation(),
    onClick = {
      onClick()
    }
  ) {
    Box(
      modifier = Modifier
        .fillMaxWidth()
        .aspectRatio(0.8f),
      contentAlignment = Alignment.Center
    ) {
      Image(
        painter = painter,
        contentDescription = null,
        modifier = Modifier
          .fillMaxSize(),
        contentScale = ContentScale.Crop
      )
      if (painterState is AsyncImagePainter.State.Loading) {
        CircularProgressIndicator()
      }
      if (painterState is AsyncImagePainter.State.Error) {
        Text(
          text = "Error while loading image, try again later!",
          color = MaterialTheme.colorScheme.error
        )
      }
    }
    Box(modifier = Modifier
      .height(56.dp)
      .padding(8.dp)) {
      Text(
        text = plant.name!!,
        maxLines = 2,
        overflow = TextOverflow.Ellipsis
      )
    }
  }
}