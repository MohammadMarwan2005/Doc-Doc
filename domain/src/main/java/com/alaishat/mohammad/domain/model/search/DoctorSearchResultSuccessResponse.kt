package com.alaishat.mohammad.domain.model.search

import com.alaishat.mohammad.domain.model.core.Doctor

data class DoctorSearchResultSuccessResponse(
    val code: Int,
    var `data`: List<Doctor>,
    val message: String,
    val status: Boolean
) : DoctorSearchResultResponse