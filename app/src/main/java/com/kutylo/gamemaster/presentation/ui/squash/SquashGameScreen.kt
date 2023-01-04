package com.kutylo.gamemaster.presentation.ui.squash

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.ButtonDefaults
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.Text
import com.kutylo.gamemaster.presentation.Player


@Composable
fun SquashGameApp(
    isGameEnded: MutableState<Boolean>,
    onGameIsEnded: () -> Unit,
    player1: Player,
    player2: Player
) {
    GameSet(isGameEnded, onGameIsEnded, player1, player2)
}

@Composable
fun GameSet(
    isGameEnded: MutableState<Boolean>,
    onGameIsEnded: () -> Unit,
    player1: Player,
    player2: Player
) {
    if (isGameEnded.value) onGameIsEnded()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ScoreView(isGameEnded, player1, player2)
        ScoreView(isGameEnded, player2, player1)
    }
}

@Composable
fun ScoreView(
    isGameEnded: MutableState<Boolean>,
    currentPlayer: Player,
    player2: Player,
) {
    Row(
        Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = "${currentPlayer.name.subSequence(0, 1)[0]}",
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
            text = "${currentPlayer.games.value}",
            fontSize = 36.sp,
            modifier = Modifier
                .widthIn(
                    min = 30.dp,
                )
        )

        Spacer(modifier = Modifier.width(4.dp))
        AddPointButton(isGameEnded, currentPlayer, player2)
    }
}

@Composable
fun AddPointButton(
    isGameEnded: MutableState<Boolean>, currentPlayer: Player, otherPlayer: Player
) {
    Button(
        modifier = Modifier.defaultMinSize(minWidth = 43.dp, minHeight = 1.dp),
        onClick = {
            val end = countPoints(currentPlayer, otherPlayer)
            isGameEnded.value = end
        },
        colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.background)
    ) {
        Text(
            text = currentPlayer.points.value.toString(),
            color = MaterialTheme.colors.primary,
            fontSize = 36.sp
        )
    }
}

fun countPoints(currentPlayer: Player, otherPlayer: Player): Boolean {

    currentPlayer.points.value++
    if (currentPlayer.points.value == 11) {
        if (currentPlayer.games.value != 3) {
            currentPlayer.games.value++
            currentPlayer.points.value = 0;
            otherPlayer.points.value = 0;
        } else {
            return true;
        }
    }
    return false;
}