package com.alaishat.mohammad.domain.model.login



data class UserLoginFailedResponse(
    val code: Int,
    val `data`: List<Any>,
    val message: String,
    val status: Boolean
) : UserLoginResponse