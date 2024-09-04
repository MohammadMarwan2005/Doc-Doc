package com.alaishat.mohammad.docdoc.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alaishat.mohammad.data.remote.APIService
import com.alaishat.mohammad.domain.model.appointments.allappointments.AllAppointmentsResponse
import com.alaishat.mohammad.domain.model.core.LocalError
import com.alaishat.mohammad.domain.usecase.GetUserTokenAndNameUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Mohammad Al-Aishat on Aug/26/2024.
 * DocDoc Project.
 */

@HiltViewModel
class AllAppointmentsViewModel @Inject constructor(
    private val getUserTokenAndNameUseCase: GetUserTokenAndNameUseCase,
    private val apiService: APIService,
) : ViewModel() {

    private val _allAppointmentsResponse: MutableStateFlow<AllAppointmentsResponse?> = MutableStateFlow(null)
    val allAppointmentsResponse = _allAppointmentsResponse.asStateFlow()
    private val _isLoading: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    init {
        getAllAppointments()
    }


    private fun getAllAppointments() {
        viewModelScope.launch {
            if (isLoading.value) return@launch
            val token = getUserTokenAndNameUseCase()
            token.first?.let {
                _isLoading.value = true
                _allAppointmentsResponse.value = try {
                    apiService.getAllAppointments(it)
                } catch (e: Exception) {
                    LocalError()
                }
                _isLoading.value = false
            }
        }
    }

    fun resetAllAppointmentsState() {
        _allAppointmentsResponse.value = null
    }
}