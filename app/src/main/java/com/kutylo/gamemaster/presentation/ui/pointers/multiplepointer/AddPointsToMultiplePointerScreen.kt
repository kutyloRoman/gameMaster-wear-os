package com.kutylo.gamemaster.presentation.ui.pointers.multiplepointer

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.Icon
import androidx.wear.compose.material.Text
import com.kutylo.gamemaster.R
import com.kutylo.gamemaster.presentation.PointerPlayer
import com.kutylo.gamemaster.presentation.navigation.Screen
import com.kutylo.gamemaster.presentation.theme.GameMasterTheme

@Composable
fun AddPointsToMultiplePointer(player: PointerPlayer, swipeDismissableNavController: NavHostController) {
    val focusManager = LocalFocusManager.current
    var pointsAmount by remember {
        mutableStateOf("")
    }

    GameMasterTheme {
        Column(
            Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 5.dp),
                text = "${player.name}: ${player.points}", maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Center
            )
            EditNumberField(modifier = Modifier.width(150.dp),
                value = pointsAmount,
                label = "Points",
                onValueChange = { pointsAmount = it },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Go
                ),
                keyboardActions = KeyboardActions(
                    onGo = { focusManager.clearFocus() }
                ))

            Row(horizontalArrangement = Arrangement.Center) {
                Button(modifier = Modifier.padding(end = 25.dp),
                    onClick = {
                        player.points = player.points + pointsAmount.toInt()
                        swipeDismissableNavController.navigate(
                            Screen.MultiplePointer.route
                        ) {
                            launchSingleTop = true
                            popUpTo(Screen.Landing.route)
                        }
                    }) {
                    Icon(imageVector = Icons.Default.Add, contentDescription = null)
                }
                Button(
                    modifier = Modifier.padding(start = 25.dp),
                    onClick = {
                        player.points = player.points - pointsAmount.toInt()
                        swipeDismissableNavController.navigate(
                            Screen.MultiplePointer.route
                        ) {
                            launchSingleTop = true
                            popUpTo(Screen.Landing.route)
                        }
                    }) {
                    Icon(
                        painter = painterResource(id = R.drawable.remove_icon),
                        contentDescription = null
                    )
                }
            }
        }
    }
}

@Composable
fun EditNumberField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    keyboardOptions: KeyboardOptions,
    keyboardActions: KeyboardActions,
    modifier: Modifier = Modifier
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier
            .padding(start = 25.dp, end = 25.dp, bottom = 5.dp)
            .fillMaxWidth(),
        singleLine = true,
        label = { Text(text = label, textAlign = TextAlign.Center) },
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions
    )

}