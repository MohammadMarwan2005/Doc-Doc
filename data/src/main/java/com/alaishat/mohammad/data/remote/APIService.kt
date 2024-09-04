package com.alaishat.mohammad.data.remote

import com.alaishat.mohammad.domain.model.all_doctors.AllDoctorsResponse
import com.alaishat.mohammad.domain.model.all_specialization.response.AllSpecializationsResponse
import com.alaishat.mohammad.domain.model.appointments.allappointments.AllAppointmentsResponse
import com.alaishat.mohammad.domain.model.appointments.bookappointment.BookAppointmentRequest
import com.alaishat.mohammad.domain.model.appointments.bookappointment.BookAppointmentResponse
import com.alaishat.mohammad.domain.model.doctor_details.DoctorDetailsResponse
import com.alaishat.mohammad.domain.model.filtered_doctors.FilteredDoctorsResponse
import com.alaishat.mohammad.domain.model.home.HomeResponse
import com.alaishat.mohammad.domain.model.login.UserLoginRequest
import com.alaishat.mohammad.domain.model.login.UserLoginResponse
import com.alaishat.mohammad.domain.model.register.RegisterResponse
import com.alaishat.mohammad.domain.model.register.UserRegisterRequest
import com.alaishat.mohammad.domain.model.search.DoctorSearchResultResponse
import com.alaishat.mohammad.domain.model.show_profile.UserProfileResponse

/**
 * Created by Mohammad Al-Aishat on Aug/20/2024.
 * DocDoc Project.
 */
interface APIService {
    suspend fun register(userRegisterRequest: UserRegisterRequest): RegisterResponse

    suspend fun login(userLoginRequest: UserLoginRequest): UserLoginResponse

    suspend fun getAllSpecializations(token: String): AllSpecializationsResponse

    suspend fun getAllDoctors(token: String): AllDoctorsResponse

    suspend fun getFilteredDoctorsByCity(token: String, id:Int): FilteredDoctorsResponse

    suspend fun getFilteredDoctorsBySpecialization(token: String, specId: Int): FilteredDoctorsResponse

    suspend fun getDoctorDetailsById(token: String, doctorId: Int): DoctorDetailsResponse

    suspend fun bookAppointment(token: String, bookAppointmentRequest: BookAppointmentRequest): BookAppointmentResponse

    suspend fun getAllAppointments(token: String): AllAppointmentsResponse

    suspend fun searchForDoctors(token: String, query: String): DoctorSearchResultResponse

    suspend fun getUserProfile(token: String): UserProfileResponse

    suspend fun getHomeResponse(token: String): HomeResponse
}