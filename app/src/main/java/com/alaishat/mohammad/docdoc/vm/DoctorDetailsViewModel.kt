package com.alaishat.mohammad.docdoc.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alaishat.mohammad.data.remote.APIService
import com.alaishat.mohammad.domain.model.core.LocalError
import com.alaishat.mohammad.domain.model.doctor_details.DoctorDetailsResponse
import com.alaishat.mohammad.domain.usecase.GetUserTokenAndNameUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Mohammad Al-Aishat on Aug/24/2024.
 * DocDoc Project.
 */
@HiltViewModel
class DoctorDetailsViewModel @Inject constructor(
    private val getUserTokenAndNameUseCase: GetUserTokenAndNameUseCase,
    private val apiService: APIService,
) : ViewModel() {

    private val _doctorDetailsResponse: MutableStateFlow<DoctorDetailsResponse?> = MutableStateFlow(null)
    val doctorDetailsResponse = _doctorDetailsResponse.asStateFlow()

    private val _isLoadingDoctorResponse: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isLoadingDoctorResponse = _isLoadingDoctorResponse.asStateFlow()


    fun getDoctorDetails(doctorId: Int) {
        viewModelScope.launch {
            getUserTokenAndNameUseCase().first?.let { token ->
                _doctorDetailsResponse.value = try {
                    apiService.getDoctorDetailsById(token, doctorId)
                } catch (e: Exception) {
                    LocalError()
                }
            }
        }
    }

    fun resetDoctorDetailsState() {
        _doctorDetailsResponse.value = null
    }

}