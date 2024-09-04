package com.alaishat.mohammad.domain.model.doctor_details

import com.alaishat.mohammad.domain.model.core.Doctor

data class DoctorDetailsSuccessResponse(
    val code: Int,
    val `data`: Doctor,
    val message: String,
    val status: Boolean
): DoctorDetailsResponse