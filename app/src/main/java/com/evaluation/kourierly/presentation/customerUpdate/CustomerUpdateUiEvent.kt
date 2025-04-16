package com.evaluation.kourierly.presentation.customerUpdate

sealed class CustomerUpdateUiEvent {
    data class UpdateCustomer(
        val name: String,
        val gender: String,
    ) : CustomerUpdateUiEvent()

    object UserMessageShown : CustomerUpdateUiEvent()
}
