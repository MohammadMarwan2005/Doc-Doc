package com.alaishat.mohammad.domain.model.appointments.bookappointment

import kotlinx.serialization.Serializable

/**
 * Created by Mohammad Al-Aishat on Aug/25/2024.
 * DocDoc Project.
 */
@Serializable
data class BookAppointmentRequest(
    val doctor_id: Int,
    val start_time : String,
    val notes: String
)
