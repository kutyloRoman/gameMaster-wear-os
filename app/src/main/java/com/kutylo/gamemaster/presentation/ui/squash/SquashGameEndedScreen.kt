package com.kutylo.gamemaster.presentation.ui.squash

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.Icon
import androidx.wear.compose.material.Text
import com.kutylo.gamemaster.R
import com.kutylo.gamemaster.presentation.theme.GameMasterTheme

@Composable
fun SquashGameEndedScreen(onEndOkClick: () -> Unit) {
    GameMasterTheme {
        Column(
            Modifier
                .fillMaxHeight()
                .padding(5.dp)
                .wrapContentSize(Alignment.Center),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(R.string.game_over_label),
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(5.dp))
            Button(modifier = Modifier.align(CenterHorizontally), onClick = { onEndOkClick() }) {
                Icon(imageVector = Icons.Default.Done, contentDescription = null)
            }
        }
    }

}

@Preview(device = Devices.WEAR_OS_SMALL_ROUND, showSystemUi = true, showBackground = true)
@Composable
fun SquashGameEndedScreenPreview() {
    SquashGameEndedScreen({})
}