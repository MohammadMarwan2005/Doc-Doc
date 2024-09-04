package com.alaishat.mohammad.domain.model.register

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Created by Mohammad Al-Aishat on Aug/20/2024.
 * DocDoc Project.
 */

@Serializable
data class UserRegisterRequest(
    val name: String,
    val email: String,
    val phone: String,
    val password: String,
    val gender: String,
    @SerialName("password_confirmation") val passwordConfirmation: String,
)
