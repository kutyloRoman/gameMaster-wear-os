package com.kutylo.gamemaster.presentation.ui.pointers.singlepointer

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.wear.compose.material.Icon
import androidx.wear.compose.material.Stepper
import androidx.wear.compose.material.StepperDefaults
import androidx.wear.compose.material.Text
import com.kutylo.gamemaster.presentation.ui.pointers.PointerPlayerViewModel

@Composable
fun AddPointsToSinglePointer(
    singlePointerPlayerViewModel: PointerPlayerViewModel,
    playerIndex: Int
) {

    val player = singlePointerPlayerViewModel.getPlayer(index = playerIndex)
    var point by remember {
        mutableStateOf(player.points)
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 10.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Stepper(
            value = point,
            onValueChange = {
                player.points = it
                point = player.points
            },
            valueProgression = 0..10,
            increaseIcon = { Icon(StepperDefaults.Increase, "Increase") },
            decreaseIcon = { Icon(StepperDefaults.Decrease, "Decrease") }
        ) {
            Text("Value: ${point}")
        }
    }
}