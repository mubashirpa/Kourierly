package com.evaluation.kourierly.presentation.customerUpdate

sealed class CustomerUpdateUiEvent {
    data class OnGenderDropdownExpandedChange(
        val expanded: Boolean,
    ) : CustomerUpdateUiEvent()

    data class OnRolesDropdownExpandedChange(
        val expanded: Boolean,
    ) : CustomerUpdateUiEvent()

    data class UpdateCustomer(
        val name: String,
        val gender: String,
    ) : CustomerUpdateUiEvent()

    object UserMessageShown : CustomerUpdateUiEvent()
}
