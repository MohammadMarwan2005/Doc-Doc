package com.alaishat.mohammad.domain.model.appointments.bookappointment.invalidtime

import kotlinx.serialization.SerialName

data class InvalidTimeData(
    @SerialName("start_time") val startTime: List<String>
)