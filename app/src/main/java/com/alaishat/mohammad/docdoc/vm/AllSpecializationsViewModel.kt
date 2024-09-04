package com.alaishat.mohammad.docdoc.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alaishat.mohammad.data.remote.APIService
import com.alaishat.mohammad.domain.model.all_specialization.response.AllSpecializationsResponse
import com.alaishat.mohammad.domain.model.core.LocalError
import com.alaishat.mohammad.domain.model.filtered_doctors.FilteredDoctorsResponse
import com.alaishat.mohammad.domain.usecase.GetUserTokenAndNameUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Mohammad Al-Aishat on Aug/31/2024.
 * DocDoc Project.
 */

@HiltViewModel
class AllSpecializationsViewModel @Inject constructor(
    private val getUserTokenAndNameUseCase: GetUserTokenAndNameUseCase,
    private val apiService: APIService,
) : ViewModel() {


    private val _allSpecializationsResponse: MutableStateFlow<AllSpecializationsResponse?> = MutableStateFlow(null)
    val allSpecializationsResponse = _allSpecializationsResponse.asStateFlow()

    private val _isLoadingAllSpec: MutableStateFlow<Boolean> = MutableStateFlow(false)
    private val isLoadingAllSpec = _isLoadingAllSpec.asStateFlow()

    private val _filteredDoctorsBySpecResponse: MutableStateFlow<FilteredDoctorsResponse?> = MutableStateFlow(null)
    val filteredDoctorsBySpecResponse = _filteredDoctorsBySpecResponse.asStateFlow()

    private val _isLoadingFilteredDoctorsBySpec: MutableStateFlow<Boolean> = MutableStateFlow(false)
    private val isLoadingFilteredDoctorsBySpec = _isLoadingFilteredDoctorsBySpec.asStateFlow()


    fun getAllSpecializationsResponse() {
        viewModelScope.launch {
            if (isLoadingAllSpec.value) return@launch
            val token = getUserTokenAndNameUseCase().first
            token?.let {
                _isLoadingAllSpec.value = true
                _allSpecializationsResponse.value = try {
                    apiService.getAllSpecializations(it)
                } catch (e: Exception) {
                    LocalError()
                }

                _isLoadingAllSpec.value = false
            }
        }
    }


    fun getFilteredDoctorsBySpec(specId: Int) {
        viewModelScope.launch {
            if (isLoadingFilteredDoctorsBySpec.value) return@launch
            val token = getUserTokenAndNameUseCase().first
            token?.let {
                _isLoadingFilteredDoctorsBySpec.value = true
                _filteredDoctorsBySpecResponse.value = try {
                    apiService.getFilteredDoctorsBySpecialization(token, specId)
                } catch (e: Exception) {
                    LocalError()
                }

                _isLoadingFilteredDoctorsBySpec.value = false
            }
        }
    }

    fun resetFilteredDoctorsBySpec() {
        _filteredDoctorsBySpecResponse.value = null
//        _isLoadingFilteredDoctorsBySpec.value
    }

    fun resetAllSpecializationState() {
        _allSpecializationsResponse.value = null
        _isLoadingAllSpec.value = false
    }

}