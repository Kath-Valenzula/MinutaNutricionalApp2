package com.example.minutanutricionalapp2.data

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

object CalorieGoalRepository {
    // Meta por defecto (0 = no definida)
    private val _goal = MutableStateFlow(0)
    val goal: StateFlow<Int> = _goal

    fun setGoal(value: Int) {
        _goal.value = value.coerceAtLeast(0)
    }
}
