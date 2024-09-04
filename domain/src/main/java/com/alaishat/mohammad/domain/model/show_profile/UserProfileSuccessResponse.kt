package com.alaishat.mohammad.domain.model.show_profile

data class UserProfileSuccessResponse(
    val code: Int,
    val `data`: List<Data>,
    val message: String,
    val status: Boolean
) : UserProfileResponse