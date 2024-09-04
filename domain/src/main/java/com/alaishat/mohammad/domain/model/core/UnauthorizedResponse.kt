package com.alaishat.mohammad.domain.model.core

import com.alaishat.mohammad.domain.model.all_doctors.AllDoctorsResponse
import com.alaishat.mohammad.domain.model.all_specialization.response.AllSpecializationsResponse
import com.alaishat.mohammad.domain.model.appointments.allappointments.AllAppointmentsResponse
import com.alaishat.mohammad.domain.model.appointments.bookappointment.BookAppointmentResponse
import com.alaishat.mohammad.domain.model.doctor_details.DoctorDetailsResponse
import com.alaishat.mohammad.domain.model.filtered_doctors.FilteredDoctorsResponse
import com.alaishat.mohammad.domain.model.home.HomeResponse
import com.alaishat.mohammad.domain.model.search.DoctorSearchResultResponse
import com.alaishat.mohammad.domain.model.show_profile.UserProfileResponse

/**
 * Created by Mohammad Al-Aishat on Aug/24/2024.
 * DocDoc Project.
 */

data class UnauthorizedResponse(
    val code: Int,
    val `data`: List<Any>,
    val message: String,
    val status: Boolean,
) : AllDoctorsResponse,
    AllSpecializationsResponse,
    DoctorDetailsResponse,
    FilteredDoctorsResponse,
    BookAppointmentResponse,
    AllAppointmentsResponse,
    DoctorSearchResultResponse,
    UserProfileResponse,
    HomeResponse