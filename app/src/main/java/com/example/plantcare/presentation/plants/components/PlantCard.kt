package com.example.plantcare.presentation.plants.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import java.text.SimpleDateFormat

@Composable
fun PlantCard(
  imageURL: String?,
  name: String?,
  dateAdded: Long?,
  modifier: Modifier,
) {

  val painter =
    rememberAsyncImagePainter(model = imageURL)
  val painterState = painter.state

  Card(
    modifier = modifier,
    elevation = 8.dp,
    shape = RoundedCornerShape(12.dp),
  ) {
    Column(
      verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
      Box(
        modifier = Modifier
          .height(200.dp)
          .fillMaxWidth(),
        contentAlignment = Alignment.Center
      ) {
        Image(
          painter = painter,
          contentDescription = null,
          modifier = Modifier.fillMaxSize(),
          contentScale = ContentScale.Crop
        )
        if (painterState is AsyncImagePainter.State.Loading) {
          CircularProgressIndicator()
        }
      }
      Text(
        text = name?: "Error",
        modifier = Modifier.padding(horizontal = 16.dp),
        fontSize = MaterialTheme.typography.h6.fontSize,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis
      )
      Text(
        text = millisToDateString(dateAdded?: System.currentTimeMillis()),
        modifier = Modifier.padding(horizontal = 16.dp),
        fontSize = MaterialTheme.typography.body2.fontSize,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        color = MaterialTheme.colors.onSurface.copy(alpha = 0.5f)
      )
      Spacer(modifier = Modifier.height(4.dp))
    }
  }
}

fun millisToDateString(time: Long): String {
  val simpleDateFormat = SimpleDateFormat("dd/MM/yyyy")
  return simpleDateFormat.format(time)
}