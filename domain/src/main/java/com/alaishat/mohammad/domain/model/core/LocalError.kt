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

data class LocalError(
    val message: String = "Local Error: Something Went Wrong",
) : AllDoctorsResponse,
    AllSpecializationsResponse,
    DoctorDetailsResponse,
    FilteredDoctorsResponse,
    BookAppointmentResponse,
    AllAppointmentsResponse,
    DoctorSearchResultResponse,
    UserProfileResponse,
    HomeResponse
