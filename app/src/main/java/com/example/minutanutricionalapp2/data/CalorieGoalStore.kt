package com.example.minutanutricionalapp2.data

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

object CalorieGoalStore {
    var goalKcal by mutableStateOf(0)
        private set

    fun setGoal(kcal: Int) { goalKcal = maxOf(0, kcal) }
    fun reset() { goalKcal = 0 }
}
