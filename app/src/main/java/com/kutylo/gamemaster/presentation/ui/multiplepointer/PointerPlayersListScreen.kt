package com.kutylo.gamemaster.presentation.ui.multiplepointer

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.*
import com.kutylo.gamemaster.presentation.PointerPlayer


//TODO: ability to add players

@Composable
fun MultiplePointerApp(
    players: List<PointerPlayer>,
    listState: ScalingLazyListState,
    onClickPlayer: (Int) -> Unit,
    onClickAddPlayer: () -> Unit
) {
    ScalingLazyColumn(
        modifier = Modifier.fillMaxWidth(),
        anchorType = ScalingLazyListAnchorType.ItemStart,
        state = listState,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        autoCentering = AutoCenteringParams(itemIndex = 0, itemOffset = 0)
    ) {
        items(players) { player ->
            PlayerChip(player = player, onClickPlayer)
        }

        //Add Player Button
        item {
            Chip(
                onClick = {onClickAddPlayer()},
                label = {
                    Text(text = "Add Player")
                })
        }
    }
}

@Composable
fun PlayerChip(player: PointerPlayer, onClickPlayer: (Int) -> Unit) {
    Chip(
        onClick = { onClickPlayer(player.index) },
        modifier = Modifier.fillMaxWidth(),
        label = {
            Text(
                text = "${player.name}: ${player.points}", maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    )
}

