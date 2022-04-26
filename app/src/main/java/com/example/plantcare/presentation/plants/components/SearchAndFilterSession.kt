package com.example.plantcare.presentation.plants.components

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.plantcare.domain.utils.OrderType
import com.example.plantcare.domain.utils.PlantOrder

@Composable
fun SearchAndFilterSession(
  modifier: Modifier = Modifier,
  plantOrder: PlantOrder  = PlantOrder.DateAdded(OrderType.Descending),
  onOrderChange: (PlantOrder) -> Unit
) {

  Column(
    modifier = modifier
  ) {
    Row(
      modifier = Modifier.fillMaxWidth()
    ) {
      DefaultRadioButton(
        text = "Date",
        checked = plantOrder is PlantOrder.DateAdded,
        onSelect = {
          onOrderChange(PlantOrder.DateAdded(plantOrder.orderType))
        }
      )
      Spacer(modifier = Modifier.width(8.dp))
      DefaultRadioButton(
        text = "Name",
        checked = plantOrder is PlantOrder.Name,
        onSelect = {
          onOrderChange(PlantOrder.Name(plantOrder.orderType))
        }
      )
      Spacer(modifier = Modifier.width(4.dp))
      DefaultRadioButton(
        text = "Description",
        checked = plantOrder is PlantOrder.Description,
        onSelect = {
          onOrderChange(PlantOrder.Description(plantOrder.orderType))
        }
      )
    }
    Spacer(modifier = Modifier.height(8.dp))
    Row(
      modifier = Modifier.fillMaxWidth()
    ) {
      DefaultRadioButton(
        text = "Ascending",
        checked = plantOrder.orderType is OrderType.Ascending,
        onSelect = {
          onOrderChange( plantOrder.copy(OrderType.Ascending))
        }
      )
      Spacer(modifier = Modifier.width(8.dp))
      DefaultRadioButton(
        text = "Descending",
        checked = plantOrder.orderType is OrderType.Descending,
        onSelect = {
          onOrderChange(plantOrder.copy(OrderType.Descending))
        }
      )
    }
  }
}