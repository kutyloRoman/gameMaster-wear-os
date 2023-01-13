package com.kutylo.gamemaster.presentation.data

import androidx.compose.runtime.MutableState

/**
 * Simple Model representing pointer player.
 */
data class SquashPlayerModel(val name: String, var points: MutableState<Int>, var games: MutableState<Int>)