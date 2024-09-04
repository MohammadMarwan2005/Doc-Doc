package com.alaishat.mohammad.domain.model.all_doctors.success

import com.alaishat.mohammad.domain.model.all_doctors.AllDoctorsResponse
import com.alaishat.mohammad.domain.model.core.Doctor

data class AllDoctorsSuccessResponse(
    val code: Int,
    val `data`: List<Doctor>,
    val message: String,
    val status: Boolean
): AllDoctorsResponse