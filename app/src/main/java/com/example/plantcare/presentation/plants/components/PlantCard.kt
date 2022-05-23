package com.example.plantcare.presentation.plants.components

import android.annotation.SuppressLint
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
import java.text.SimpleDateFormat

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlantCard(
  plant: Plant,
  modifier: Modifier,
) {

  val painter = rememberAsyncImagePainter( model = plant.imageURL )
  val painterState = painter.state

  Card(
    modifier = modifier,
    elevation = CardDefaults.elevatedCardElevation()
  ) {
    Column(
      verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
      Box(
        modifier = Modifier
          .aspectRatio(0.8f)
        ,
        contentAlignment = Alignment.Center
      ) {
        Image(
          painter = painter,
          contentDescription = null,
          modifier = Modifier.fillMaxSize(),
          contentScale = ContentScale.Crop
        )
        when (painterState) {
          is AsyncImagePainter.State.Loading -> {
              CircularProgressIndicator()
          }
          is AsyncImagePainter.State.Success -> {
          }
          is AsyncImagePainter.State.Error -> {
            Text(
              text = "Error while loading image, try again later!",
              color = MaterialTheme.colorScheme.error,
              modifier = Modifier.padding(16.dp)
            )
          }
          is AsyncImagePainter.State.Empty -> {
            Text(text = "Nothing to show!")
          }
        }
      }
      Text(
        text = plant.name!!,
        modifier = Modifier.padding(horizontal = 16.dp),
        fontSize = MaterialTheme.typography.titleLarge.fontSize,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        color = MaterialTheme.colorScheme.onSurface,
      )
      Text(
        text = millisToDateString(plant.dateAdded ?: System.currentTimeMillis()),
        modifier = Modifier.padding(horizontal = 16.dp),
        fontSize = MaterialTheme.typography.titleSmall.fontSize,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        color = MaterialTheme.colorScheme.onSurfaceVariant
      )
      Spacer(modifier = Modifier.height(4.dp))
    }
  }
}

@SuppressLint("SimpleDateFormat")
fun millisToDateString(time: Long): String {
  val simpleDateFormat = SimpleDateFormat("dd/MM/yyyy")
  return simpleDateFormat.format(time)
}