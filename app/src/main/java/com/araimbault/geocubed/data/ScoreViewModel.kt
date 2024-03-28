package com.araimbault.geocubed.data

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.lifecycle.ViewModel

class ScoreViewModel : ViewModel() {
    private var _score: MutableState<Int> = mutableIntStateOf(0)

    val score: MutableState<Int> = _score

    fun increaseScore() {
        _score.value = _score.value + 1
    }

    fun decreaseScore() {
        _score.value = _score.value - 1
    }
}