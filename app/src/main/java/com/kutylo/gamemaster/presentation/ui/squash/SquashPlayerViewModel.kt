package com.kutylo.gamemaster.presentation.ui.squash

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.kutylo.gamemaster.presentation.data.SquashPlayerModel

class SquashPlayerViewModel() : ViewModel() {
    var player1 = SquashPlayerModel(
        "Roman",
        mutableStateOf(0),
        mutableStateOf(0)
    )
    var player2 = SquashPlayerModel(
        "Marta",
        mutableStateOf(0),
        mutableStateOf(0)
    )

    private var _squashPlayers = mutableListOf<SquashPlayerModel>(player1, player2)
    private var _isGameEnded = mutableStateOf(false)

    //Getters
    val squashPlayers: List<SquashPlayerModel>
        get() = _squashPlayers

    val isGameEnded: Boolean
        get() = _isGameEnded.value

    //Field Getters
    fun getPlayer(index: Int): SquashPlayerModel {
        return squashPlayers[index]
    }

    fun getPlayerName(id:Int): String{
        return getPlayer(id).name
    }

    fun getPlayerPoints(id:Int): Int{
        return getPlayer(id).points.value
    }

    fun getPlayerGames(id:Int): Int{
        return getPlayer(id).games.value
    }

    //Setters
    fun setIsGameEnded(isGameEnded: Boolean) {
        _isGameEnded.value = isGameEnded
    }

    //Game functions
    fun updatePlayerPoints(currentPlayerId: Int, otherPlayerId: Int) {
        getPlayer(currentPlayerId).let {
            it.points.value++

            if (it.points.value == 11){
                if (it.games.value != 3){
                    it.games.value ++
                    it.points.value = 0
                    getPlayer(otherPlayerId).let {
                        it.points.value = 0
                    }
                }else {
                    setIsGameEnded(true)
                }
            }
        }
    }

    fun clearPointsAndGames() {
        squashPlayers.forEach {
            it.points.value = 0
            it.games.value = 0
        }
    }
}