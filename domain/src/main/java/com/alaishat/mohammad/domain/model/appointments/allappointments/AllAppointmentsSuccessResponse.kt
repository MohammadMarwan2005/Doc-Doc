package com.alaishat.mohammad.domain.model.appointments.allappointments

import com.alaishat.mohammad.domain.model.appointments.core.AppointmentData
import kotlinx.serialization.SerialName

data class AllAppointmentsSuccessResponse(
    val code: Int,
    val `data`: List<AppointmentData>,
    val message: String,
    val status: Boolean
) : AllAppointmentsResponse