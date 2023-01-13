package com.kutylo.gamemaster.presentation.ui.squash

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.ButtonDefaults
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.Text


@Composable
fun SquashGameApp(
    onGameIsEnded: () -> Unit,
    squashPlayerViewModel: SquashPlayerViewModel
) {
    GameSet(onGameIsEnded, squashPlayerViewModel)
}

@Composable
fun GameSet(
    onGameIsEnded: () -> Unit,
    squashPlayerViewModel: SquashPlayerViewModel
) {
    if (squashPlayerViewModel.isGameEnded) onGameIsEnded()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ScoreView(
            squashPlayerViewModel,
            0,
            1
        )
        ScoreView(
            squashPlayerViewModel,
            1,
            0
        )
    }
}

@Composable
fun ScoreView(
    squashPlayerViewModel: SquashPlayerViewModel,
    currentPlayerIndex: Int,
    player2Index: Int,
) {
    Row(
        Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = "${
                squashPlayerViewModel.getPlayerName(currentPlayerIndex).subSequence(0, 1)[0]
            }",
            fontSize = 36.sp,
            modifier = Modifier
                .widthIn(
                    min = 30.dp
                )
        )
        Text(
            text = ":",
            fontSize = 36.sp,
            modifier = Modifier
                .padding(horizontal = 5.dp)
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = "${squashPlayerViewModel.getPlayerGames(currentPlayerIndex)}",
            fontSize = 36.sp,
            modifier = Modifier
                .widthIn(
                    min = 30.dp,
                )
        )

        Spacer(modifier = Modifier.width(4.dp))
        AddPointButton(squashPlayerViewModel, currentPlayerIndex, player2Index)
    }
}

@Composable
fun AddPointButton(
    squashPlayerViewModel: SquashPlayerViewModel,
    currentPlayerIndex: Int,
    otherPlayerIndex: Int
) {
    Button(
        modifier = Modifier.defaultMinSize(minWidth = 43.dp, minHeight = 1.dp),
        onClick = {
            squashPlayerViewModel.updatePlayerPoints(currentPlayerIndex, otherPlayerIndex)
        },
        colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.background)
    ) {
        Text(
            text = squashPlayerViewModel.getPlayerPoints(currentPlayerIndex).toString(),
            color = MaterialTheme.colors.primary,
            fontSize = 36.sp
        )
    }
}