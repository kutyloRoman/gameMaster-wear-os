package com.kutylo.gamemaster.presentation.ui.pointers.multiplepointer

import android.app.RemoteInput
import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.wear.compose.material.*
import androidx.wear.input.RemoteInputIntentHelper
import androidx.wear.input.wearableExtender
import com.kutylo.gamemaster.presentation.data.PointerPlayerModel


@Composable
fun MultiplePointerApp(
    players: List<PointerPlayerModel>,
    listState: ScalingLazyListState,
    onClickPlayer: (Int) -> Unit,
    onClickAddPlayer: (value: String) -> Unit
) {
    val inputTextKey = "input_text"

    val launcher =
        rememberLauncherForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            it.data?.let { data ->
                val results: Bundle = RemoteInput.getResultsFromIntent(data)
                val newInputText: CharSequence? = results.getCharSequence(inputTextKey)
                onClickAddPlayer(newInputText.toString())
            }
        }

    ScalingLazyColumn(
        modifier = Modifier.fillMaxWidth(),
        anchorType = ScalingLazyListAnchorType.ItemStart,
        state = listState,
        horizontalAlignment = Alignment.CenterHorizontally,
//        verticalArrangement = Arrangement.spacedBy(8.dp),
        autoCentering = AutoCenteringParams(itemIndex = 0, itemOffset = 0)
    ) {
        items(players) { player ->
            PlayerChip(player = player, onClickPlayer)
        }

        //Add Player Button
        item {
            val intent: Intent = RemoteInputIntentHelper.createActionRemoteInputIntent()
            val remoteInputs: List<RemoteInput> = listOf(
                RemoteInput.Builder(inputTextKey)
                    .setLabel(inputTextKey)
                    .wearableExtender {
                        setEmojisAllowed(false)
                        setInputActionType(EditorInfo.IME_ACTION_DONE)
                    }.build()
            )

            RemoteInputIntentHelper.putRemoteInputsExtra(intent, remoteInputs)

            Chip(
                onClick = { launcher.launch(intent) },
                label = {
                    Text(
                        text = "Add Player",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                })
        }
    }
}

@Composable
fun PlayerChip(player: PointerPlayerModel, onClickPlayer: (Int) -> Unit) {
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

