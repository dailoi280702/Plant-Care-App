package com.example.plantcare.presentation.plant_detail.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import com.example.plantcare.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ImageSection(
  showLocalImage: Boolean,
  painter: AsyncImagePainter,
  onClick: () -> Unit
) {

  val painterState = painter.state

  Column(modifier = Modifier.fillMaxWidth()) {
    Card(
      modifier = Modifier
//        .padding(16.dp)
        .aspectRatio(0.8f)
        .clickable {
          onClick()
        },
      elevation = CardDefaults.elevatedCardElevation()
    ) {
      Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
      ) {
        if (showLocalImage) {
          Column(
            modifier = Modifier
              .fillMaxSize()
              .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
          ) {
            IconButton(
              onClick = {
                onClick()
              },
            ) {
              Icon(
                painter = painterResource(
                  id = R.drawable.ic_image
                ),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.surfaceTint
              )
            }
            Text(
              text = "Click to choose picture",
              color = MaterialTheme.colorScheme.onSurface,
              textAlign = TextAlign.Center
            )
          }
        } else {
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
            }
          }
        }
      }
    }
  }
}

