package com.kutylo.gamemaster.presentation

import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import androidx.wear.compose.material.*
import androidx.wear.compose.navigation.SwipeDismissableNavHost
import androidx.wear.compose.navigation.composable
import androidx.wear.compose.navigation.rememberSwipeDismissableNavController
import com.example.android.wearable.composeadvanced.presentation.ui.dice.DiceRollerApp
import com.kutylo.gamemaster.R
import com.kutylo.gamemaster.presentation.navigation.Screen
import com.kutylo.gamemaster.presentation.navigation.PlayerIndex
import com.kutylo.gamemaster.presentation.theme.GameMasterTheme
import com.kutylo.gamemaster.presentation.ui.landing.LandingScreen
import com.kutylo.gamemaster.presentation.ui.pointer.AddPoints
import com.kutylo.gamemaster.presentation.ui.pointer.MultiplePointerApp
import com.kutylo.gamemaster.presentation.ui.squash.SquashGameApp
import com.kutylo.gamemaster.presentation.ui.squash.SquashGameEndedScreen

@Composable
fun WearApp(
    modifier: Modifier = Modifier,
    swipeDismissableNavController: NavHostController = rememberSwipeDismissableNavController()
) {
    val listState = rememberScalingLazyListState()
    var player1 = Player(
        "Roman",
        remember { mutableStateOf(0) },
        remember { mutableStateOf(0) })
    var player2 = Player(
        "Marta",
        remember { mutableStateOf(0) },
        remember { mutableStateOf(0) })
    var isGameEnded = remember { mutableStateOf(false) }


    //Multiple Pointer vars
    var pointerPlayer1 = PointerPlayer(
        0,
        "Roman",
        remember { mutableStateOf(0) })
    var pointerPlaye2 = PointerPlayer(
        1,
        "Marta",
        remember { mutableStateOf(0) })
    var pointerPlayes = mutableListOf(pointerPlayer1, pointerPlaye2)


    //Main Screen
    GameMasterTheme() {
        Scaffold(
            timeText = {
                TimeText()
            },
            vignette = { Vignette(vignettePosition = VignettePosition.TopAndBottom) },
            positionIndicator = {
                PositionIndicator(
                    scalingLazyListState = listState
                )
            }
        ) {
            SwipeDismissableNavHost(
                navController = swipeDismissableNavController,
                startDestination = Screen.Landing.route,
                modifier = Modifier.background(MaterialTheme.colors.background)
            ) {
                composable(Screen.Landing.route) {
                    val focusRequester = remember { FocusRequester() }

                    val menuItems = listOf(
                        menuNameAndCallback(
                            navController = swipeDismissableNavController,
                            menuNameResource = R.string.dice_label,
                            screen = Screen.Dice
                        ),
                        menuNameAndCallback(
                            navController = swipeDismissableNavController,
                            menuNameResource = R.string.squash_label,
                            screen = Screen.Squash
                        ),
                        menuNameAndCallback(
                            navController = swipeDismissableNavController,
                            menuNameResource = R.string.pointer_label,
                            screen = Screen.Squash
                        ),
                        menuNameAndCallback(
                            navController = swipeDismissableNavController,
                            menuNameResource = R.string.multiple_pointer_label,
                            screen = Screen.MultiplePointer
                        )
                    )

                    LandingScreen(
                        scalingLazyListState = listState,
                        focusRequester = focusRequester,
                        menuItems = menuItems
                    )
                }

                //DICE Main Window
                composable(Screen.Dice.route) {
                    DiceRollerApp()
                }

                //Squash Main Window
                composable(Screen.Squash.route) {
                    SquashGameApp(isGameEnded, onGameIsEnded = {
                        player1.points.value = 0
                        player2.points.value = 0
                        player1.games.value = 0
                        player2.games.value = 0
                        isGameEnded.value = false
                        swipeDismissableNavController.navigate(route = Screen.SquashGameEnd.route)
                    }, player1, player2)
                }

                //Squash End Game Window
                composable(Screen.SquashGameEnd.route) {
                    SquashGameEndedScreen(onEndOkClick = {
                        swipeDismissableNavController.navigate(route = Screen.Squash.route) {
                            launchSingleTop = true
                            popUpTo(Screen.Landing.route)
                        }
                    })
                }

                //Multiple pointer players window
                composable(Screen.MultiplePointer.route) {
                    MultiplePointerApp(players = pointerPlayes, listState) { index ->
                        swipeDismissableNavController.navigate(
                            Screen.MultiplePointerPlayer.route + "/" + index
                        ) { launchSingleTop = true }
                    }
                }

                //Multiple pointer add point window
                composable(
                    route = Screen.MultiplePointerPlayer.route + "/{$PlayerIndex}",
                    arguments = listOf(
                        navArgument(PlayerIndex) {
                            type = NavType.IntType
                        })
                ) {
                    val index: Int = it.arguments!!.getInt(PlayerIndex)
                    AddPoints(player = pointerPlayes.get(index), swipeDismissableNavController)
                }
            }
        }
    }
}

@Composable
private fun menuNameAndCallback(
    navController: NavHostController,
    menuNameResource: Int,
    screen: Screen
) = MenuItem(stringResource(menuNameResource)) { navController.navigate(screen.route) }


@Composable
private fun addPlayerToMultiplePointer(
    playersAmountIndex: Int,
    playersList: MutableList<PointerPlayer>
) {
    var playerPoint = remember {
        mutableStateOf(0)
    }
    val player = PointerPlayer(playersAmountIndex, "Player$playersAmountIndex +1", playerPoint)

    playersList.add(playersAmountIndex, player)
}

data class MenuItem(val name: String, val clickHander: () -> Unit)

data class Player(val name: String, var points: MutableState<Int>, var games: MutableState<Int>)

data class PointerPlayer(val index: Int, val name: String, var points: MutableState<Int>)