package com.alaishat.mohammad.docdoc.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alaishat.mohammad.data.remote.APIService
import com.alaishat.mohammad.domain.model.appointments.bookappointment.BookAppointmentRequest
import com.alaishat.mohammad.domain.model.appointments.bookappointment.BookAppointmentResponse
import com.alaishat.mohammad.domain.model.core.LocalError
import com.alaishat.mohammad.domain.usecase.GetUserTokenAndNameUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Mohammad Al-Aishat on Aug/25/2024.
 * DocDoc Project.
 */

@HiltViewModel
class BookAppointmentViewModel @Inject constructor(
    private val getUserTokenAndNameUseCase: GetUserTokenAndNameUseCase,
    private val apiService: APIService,
) : ViewModel() {

    private val _bookAppointmentResponse: MutableStateFlow<BookAppointmentResponse?> = MutableStateFlow(null)
    val bookAppointmentResponse = _bookAppointmentResponse.asStateFlow()

    private val _isLoading: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()


    fun bookAppointment(doctorId: Int, formattedDateAndTime: String, notes: String) {
        viewModelScope.launch {
            if (isLoading.value) return@launch
            val token = getUserTokenAndNameUseCase()
            token.first?.let {
                _isLoading.value = true
                _bookAppointmentResponse.value = try {
                    apiService.bookAppointment(
                        it, BookAppointmentRequest(
                            doctor_id = doctorId,
                            start_time = formattedDateAndTime,
                            notes = notes
                        )
                    )
                } catch (e: Exception) {
                    LocalError()
                }
                _isLoading.value = false
            }
        }
    }

    fun restAppointmentsState() {
        _bookAppointmentResponse.value = null
    }
}
