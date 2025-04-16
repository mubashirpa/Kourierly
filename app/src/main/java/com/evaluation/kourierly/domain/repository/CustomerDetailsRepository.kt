package com.evaluation.kourierly.domain.repository

import com.evaluation.kourierly.domain.model.CustomerDetails
import kotlinx.coroutines.flow.Flow

interface CustomerDetailsRepository {
    val customerDetailsFlow: Flow<CustomerDetails>

    suspend fun updateCustomerDetails(
        customerId: String,
        customerName: String,
        gender: String,
        phoneNumber: String,
        roleId: String,
        roleName: String,
    )

    suspend fun clearCustomerDetails()
}
