package com.evaluation.kourierly.presentation.main

import com.evaluation.kourierly.navigation.Screen

data class MainUiState(
    val loading: Boolean = false,
    val startDestination: Screen = Screen.SendOtp,
)
