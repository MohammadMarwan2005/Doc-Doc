package com.alaishat.mohammad.domain.model.login

/**
 * Created by Mohammad Al-Aishat on Aug/20/2024.
 * DocDoc Project.
 */
data class UserLoginSuccessResponse(
    val code: Int,
    val `data`: Data,
    val message: String,
    val status: Boolean,
) : UserLoginResponse
