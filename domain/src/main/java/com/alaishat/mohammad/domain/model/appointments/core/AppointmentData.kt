package com.alaishat.mohammad.domain.model.appointments.core

import com.alaishat.mohammad.domain.model.core.Doctor
import kotlinx.serialization.SerialName

data class AppointmentData(
    val appointment_end_time: String,
    val appointment_price: Int,
    val appointment_time: String,
    val doctor: Doctor,
    val id: Int,
    val notes: String,
    val patient: Patient,
    val status: String
)