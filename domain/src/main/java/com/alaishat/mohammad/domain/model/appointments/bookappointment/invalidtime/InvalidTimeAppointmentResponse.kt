package com.alaishat.mohammad.domain.model.appointments.bookappointment.invalidtime

import com.alaishat.mohammad.domain.model.appointments.bookappointment.BookAppointmentResponse

data class InvalidTimeAppointmentResponse(
    val code: Int,
    val `data`: InvalidTimeData,
    val message: String,
    val status: Boolean
): BookAppointmentResponse