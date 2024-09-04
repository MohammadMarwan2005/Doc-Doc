package com.alaishat.mohammad.domain.model.all_specialization.response.success

import com.alaishat.mohammad.domain.model.all_specialization.response.AllSpecializationsResponse

data class AllSpecializationSuccessResponse(
    val message: String,
    val `data`: List<SpecializationsFullData>,
    val code: Int,
    val status: Boolean
): AllSpecializationsResponse