package com.alaishat.mohammad.domain.model.login

import kotlinx.serialization.Serializable

/**
 * Created by Mohammad Al-Aishat on Aug/20/2024.
 * DocDoc Project.
 */

@Serializable
data class UserLoginRequest(
    val email: String,
    val password: String,
)
