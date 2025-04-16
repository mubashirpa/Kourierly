package com.evaluation.kourierly.presentation.customerUpdate

import androidx.compose.foundation.text.input.TextFieldState

data class CustomerUpdateUiState(
    val customerUpdateSuccess: Boolean = false,
    val gender: TextFieldState = TextFieldState(),
    val loading: Boolean = false,
    val name: TextFieldState = TextFieldState(),
    val userMessage: String? = null,
)
