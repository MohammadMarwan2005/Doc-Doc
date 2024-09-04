package com.alaishat.mohammad.domain.model.filtered_doctors

import com.alaishat.mohammad.domain.model.core.Doctor

data class FilteredDoctorsSuccessResponse(
    val code: Int,
    val `data`: List<Doctor>,
    val message: String,
    val status: Boolean
): FilteredDoctorsResponse