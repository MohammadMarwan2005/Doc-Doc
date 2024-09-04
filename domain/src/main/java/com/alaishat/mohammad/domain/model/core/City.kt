package com.alaishat.mohammad.domain.model.core

import kotlinx.serialization.SerialName

data class City(
    @SerialName("governorate") val governorate: Governorate?,
    val id: Int,
    val name: String
)