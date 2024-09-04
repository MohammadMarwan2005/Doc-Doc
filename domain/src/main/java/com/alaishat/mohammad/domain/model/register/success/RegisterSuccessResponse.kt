package com.alaishat.mohammad.domain.model.register.success

import com.alaishat.mohammad.domain.model.register.RegisterResponse

data class RegisterSuccessResponse(
    val message: String,
    val `data`: Data,
    val status: Boolean,
    val code: Int,
): RegisterResponse