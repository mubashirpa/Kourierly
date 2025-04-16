package com.evaluation.kourierly.core

import androidx.datastore.preferences.core.stringPreferencesKey

object Constants {
    const val KOURIERLY_SERVICE_BASE_URL = "https://dev-service.kourierly.com/kouriely-services/v1"
}

object PreferencesKeys {
    val CUSTOMER_ID = stringPreferencesKey("customer_id")
    val CUSTOMER_NAME = stringPreferencesKey("customer_name")
    val GENDER = stringPreferencesKey("gender")
    val PHONE_NUMBER = stringPreferencesKey("phone_number")
    val ROLE_ID = stringPreferencesKey("role_id")
    val ROLE_NAME = stringPreferencesKey("role_name")
}
