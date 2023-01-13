package com.kutylo.gamemaster.presentation

import androidx.compose.foundation.background
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import androidx.wear.compose.material.*
import androidx.wear.compose.navigation.SwipeDismissableNavHost
import androidx.wear.compose.navigation.composable
import androidx.wear.compose.navigation.currentBackStackEntryAsState
import androidx.wear.compose.navigation.rememberSwipeDismissableNavController
import com.example.android.wearable.composeadvanced.presentation.ui.dice.DiceRollerApp
import com.kutylo.gamemaster.R
import com.kutylo.gamemaster.presentation.data.PointerPlayerModel
import com.kutylo.gamemaster.presentation.navigation.DestinationScrollType
import com.kutylo.gamemaster.presentation.navigation.PlayerIndex
import com.kutylo.gamemaster.presentation.navigation.SCROLL_TYPE_NAV_ARGUMENT
import com.kutylo.gamemaster.presentation.navigation.Screen
import com.kutylo.gamemaster.presentation.theme.GameMasterTheme
import com.kutylo.gamemaster.presentation.ui.ScalingLazyListStateViewModel
import com.kutylo.gamemaster.presentation.ui.landing.LandingScreen
import com.kutylo.gamemaster.presentation.ui.pointers.PointerPlayerViewModel
import com.kutylo.gamemaster.presentation.ui.pointers.multiplepointer.AddPointsToMultiplePointer
import com.kutylo.gamemaster.presentation.ui.pointers.multiplepointer.MultiplePointerApp
import com.kutylo.gamemaster.presentation.ui.pointers.singlepointer.AddPointsToSinglePointer
import com.kutylo.gamemaster.presentation.ui.squash.SquashGameApp
import com.kutylo.gamemaster.presentation.ui.squash.SquashGameEndedScreen
import com.kutylo.gamemaster.presentation.ui.squash.SquashPlayerViewModel

@Composable
fun WearApp(
    modifier: Modifier = Modifier,
    swipeDismissableNavController: NavHostController = rememberSwipeDismissableNavController(),
    multiplePointerPlayerViewModel: PointerPlayerViewModel = viewModel(key = "MultiplePointer"),
    squashPlayerViewModel: SquashPlayerViewModel = viewModel(),
    singlePointerPlayerViewModel: PointerPlayerViewModel = viewModel(key = "SinglePointer")
) {
    val currentBackStackEntry by swipeDismissableNavController.currentBackStackEntryAsState()
    val scrollType = currentBackStackEntry?.arguments?.getSerializable(SCROLL_TYPE_NAV_ARGUMENT)
        ?: DestinationScrollType.NONE

    //Main Screen
    GameMasterTheme() {
        Scaffold(
            timeText = {
                TimeText()
            },
            vignette = { Vignette(vignettePosition = VignettePosition.TopAndBottom) },
            positionIndicator = {
                when (scrollType) {
                    DestinationScrollType.SCALING_LAZY_COLUMN_SCROLLING -> {
                        // Get or create the ViewModel associated with the current back stack entry
                        val scrollViewModel: ScalingLazyListStateViewModel =
                            viewModel(currentBackStackEntry!!)
                        PositionIndicator(scalingLazyListState = scrollViewModel.scrollState)
                    }
                }
            }
        ) {
            SwipeDismissableNavHost(
                navController = swipeDismissableNavController,
                startDestination = Screen.Landing.route,
                modifier = Modifier.background(MaterialTheme.colors.background)
            ) {
                composable(Screen.Landing.route,
                    arguments = listOf(
                        navArgument(SCROLL_TYPE_NAV_ARGUMENT) {
                            type = NavType.EnumType(DestinationScrollType::class.java)
                            defaultValue = DestinationScrollType.SCALING_LAZY_COLUMN_SCROLLING
                        }
                    )) {
                    val scalingLazyListState = scalingLazyListState(it)
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
                            screen = Screen.SinglePointer
                        ),
                        menuNameAndCallback(
                            navController = swipeDismissableNavController,
                            menuNameResource = R.string.multiple_pointer_label,
                            screen = Screen.MultiplePointer
                        )
                    )

                    LandingScreen(
                        scalingLazyListState = scalingLazyListState,
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
                    SquashGameApp(onGameIsEnded = {
                        squashPlayerViewModel.clearPointsAndGames()
                        squashPlayerViewModel.setIsGameEnded(false)
                        swipeDismissableNavController.navigate(route = Screen.SquashGameEnd.route)
                    }, squashPlayerViewModel)
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

                //------------POINTERS-----------------------------------------------
                //Multiple pointer players window
                composable(Screen.MultiplePointer.route) {
                    MultiplePointerApp(
                        players = multiplePointerPlayerViewModel.pointerPlayers,
                        scalingLazyListState(it),
                        onClickPlayer = { index ->
                            swipeDismissableNavController.navigate(
                                Screen.MultiplePointerPlayer.route + "/" + index
                            ) { launchSingleTop = true }
                        },
                        onClickAddPlayer = {
                            addPlayerToPointer(
                                multiplePointerPlayerViewModel,
                                playerName = it
                            )
                        }
                    )
                }


                //Single pointer players window
                composable(Screen.SinglePointer.route) {
                    MultiplePointerApp(
                        players = singlePointerPlayerViewModel.pointerPlayers,
                        scalingLazyListState(it),
                        onClickPlayer = { index ->
                            swipeDismissableNavController.navigate(
                                Screen.SinglePointerPlayer.route + "/" + index
                            ) { launchSingleTop = true }
                        },
                        onClickAddPlayer = {
                            addPlayerToPointer(
                                singlePointerPlayerViewModel,
                                playerName = it
                            )
                        }
                    )
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
                    AddPointsToMultiplePointer(
                        pointerPlayerViewModel = multiplePointerPlayerViewModel,
                        playerIndex = index,
                        swipeDismissableNavController = swipeDismissableNavController
                    )
                }


                //Single pointer add point window
                composable(
                    route = Screen.SinglePointerPlayer.route + "/{$PlayerIndex}",
                    arguments = listOf(
                        navArgument(PlayerIndex) {
                            type = NavType.IntType
                        })
                ) {
                    val index: Int = it.arguments!!.getInt(PlayerIndex)
                    AddPointsToSinglePointer(
                        singlePointerPlayerViewModel,
                        playerIndex = index
                    )
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


private fun addPlayerToPointer(
    pointerPlayerViewModel: PointerPlayerViewModel,
    playerName: String
) {
    val player = PointerPlayerModel(pointerPlayerViewModel.playersAmount, playerName, 0)
    pointerPlayerViewModel.add(player)
}

@Composable
private fun scalingLazyListState(it: NavBackStackEntry): ScalingLazyListState {
    val scrollViewModel: ScalingLazyListStateViewModel = viewModel(it)

    return scrollViewModel.scrollState
}

data class MenuItem(val name: String, val clickHander: () -> Unit)