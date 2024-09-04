package com.alaishat.mohammad.domain.model.all_specialization.response.success

import com.alaishat.mohammad.domain.model.core.Doctor

data class SpecializationsFullData(
    val doctors: List<Doctor>,
    val id: Int,
    val name: String
)