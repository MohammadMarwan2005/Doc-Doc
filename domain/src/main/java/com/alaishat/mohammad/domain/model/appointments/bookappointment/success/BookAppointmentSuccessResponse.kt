package com.alaishat.mohammad.domain.model.appointments.bookappointment.success

import com.alaishat.mohammad.domain.model.appointments.bookappointment.BookAppointmentResponse
import com.alaishat.mohammad.domain.model.appointments.core.AppointmentData

data class BookAppointmentSuccessResponse(
    val code: Int,
    val `data`: AppointmentData,
    val message: String,
    val status: Boolean
): BookAppointmentResponse