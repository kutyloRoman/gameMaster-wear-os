package com.kutylo.gamemaster.presentation.ui.pointers

import androidx.lifecycle.ViewModel
import com.kutylo.gamemaster.presentation.data.PointerPlayerModel

class PointerPlayerViewModel() : ViewModel() {
    private var _pointerPlayers = mutableListOf<PointerPlayerModel>()
    private var _playerAmount = 0

    val pointerPlayers: List<PointerPlayerModel>
        get() = _pointerPlayers

    val playersAmount: Int
        get() = _pointerPlayers.size

    fun getPlayer(index: Int): PointerPlayerModel {
        return pointerPlayers[index]
    }

    fun add(player: PointerPlayerModel) {
        _pointerPlayers.add(player)
    }

    fun updatePlayerPoints(index: Int, points: Int) {
        pointerPlayers.get(index = index).points = points
    }
}