package com.alaishat.mohammad.domain.model.core

import com.alaishat.mohammad.domain.model.core.City
import com.alaishat.mohammad.domain.model.core.Specialization

data class Doctor(
    val address: String,
    val appoint_price: Int,
    val city: City,
    val degree: String,
    val description: String,
    val email: String,
    val end_time: String,
    val gender: String,
    val id: Int,
    val name: String,
    val phone: String,
    val photo: String,
    val specialization: Specialization,
    val start_time: String
)