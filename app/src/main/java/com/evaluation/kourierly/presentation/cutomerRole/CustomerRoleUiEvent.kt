package com.evaluation.kourierly.presentation.cutomerRole

sealed class CustomerRoleUiEvent {
    data class OnRoleSelected(
        val roleId: Int?,
    ) : CustomerRoleUiEvent()

    object UserMessageShown : CustomerRoleUiEvent()
}
