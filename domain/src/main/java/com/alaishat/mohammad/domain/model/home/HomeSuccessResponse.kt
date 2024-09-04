package com.alaishat.mohammad.domain.model.home

data class HomeSuccessResponse(
    val code: Int,
    val `data`: List<Data>,
    val message: String,
    val status: Boolean
): HomeResponse