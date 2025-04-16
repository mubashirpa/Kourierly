package com.evaluation.kourierly.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import com.evaluation.kourierly.core.PreferencesKeys
import com.evaluation.kourierly.domain.model.CustomerDetails
import com.evaluation.kourierly.domain.repository.CustomerDetailsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.io.IOException

class CustomerDetailsRepositoryImpl(
    private val dataStore: DataStore<Preferences>,
) : CustomerDetailsRepository {
    override val customerDetailsFlow: Flow<CustomerDetails>
        get() =
            dataStore.data
                .catch { exception ->
                    if (exception is IOException) {
                        emit(emptyPreferences())
                    } else {
                        throw exception
                    }
                }.map { preferences ->
                    CustomerDetails(
                        customerId = preferences[PreferencesKeys.CUSTOMER_ID] ?: "",
                        customerName = preferences[PreferencesKeys.CUSTOMER_NAME] ?: "",
                        gender = preferences[PreferencesKeys.GENDER] ?: "",
                        phoneNumber = preferences[PreferencesKeys.PHONE_NUMBER] ?: "",
                        roleId = preferences[PreferencesKeys.ROLE_ID] ?: "",
                        roleName = preferences[PreferencesKeys.ROLE_NAME] ?: "",
                    )
                }

    override suspend fun updateCustomerDetails(
        customerId: String,
        customerName: String,
        gender: String,
        phoneNumber: String,
        roleId: String,
        roleName: String,
    ) {
        dataStore.edit { details ->
            details[PreferencesKeys.CUSTOMER_ID] = customerId
            details[PreferencesKeys.CUSTOMER_NAME] = customerName
            details[PreferencesKeys.GENDER] = gender
            details[PreferencesKeys.PHONE_NUMBER] = phoneNumber
            details[PreferencesKeys.ROLE_ID] = roleId
            details[PreferencesKeys.ROLE_NAME] = roleName
        }
    }

    override suspend fun clearCustomerDetails() {
        dataStore.edit { details ->
            details.clear()
        }
    }
}
