package com.evaluation.kourierly.data.remote.dto.mobileUpdate

import kotlinx.serialization.Serializable

@Serializable
data class MobileUpdateDto(
    val `data`: List<MobileUpdateDataDto>? = null,
    val message: String? = null,
    val success: Boolean? = null,
)
