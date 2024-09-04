package com.alaishat.mohammad.docdoc.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alaishat.mohammad.data.remote.APIService
import com.alaishat.mohammad.domain.model.all_specialization.response.AllSpecializationsResponse
import com.alaishat.mohammad.domain.model.core.LocalError
import com.alaishat.mohammad.domain.model.search.DoctorSearchResultResponse
import com.alaishat.mohammad.domain.usecase.DocDocSearchFilter
import com.alaishat.mohammad.domain.usecase.GetUserTokenAndNameUseCase
import com.alaishat.mohammad.domain.usecase.SearchForDoctorsUseCase
import com.alaishat.mohammad.domain.usecase.UnspecifiedFilter
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
class SearchViewModel @Inject constructor(
    private val getUserTokenAndNameUseCase: GetUserTokenAndNameUseCase,
    private val searchForDoctorsUseCase: SearchForDoctorsUseCase,
    private val apiService: APIService,
) : ViewModel() {

    private val _searchResultResponse: MutableStateFlow<DoctorSearchResultResponse?> = MutableStateFlow(null)

    val searchResultResponse = _searchResultResponse.asStateFlow()
    private val _isLoading: MutableStateFlow<Boolean> = MutableStateFlow(false)

    val isLoading = _isLoading.asStateFlow()
    fun search(query: String, filter: DocDocSearchFilter = UnspecifiedFilter) {
        viewModelScope.launch {
            if (isLoading.value) return@launch
            val token = getUserTokenAndNameUseCase()
            token.first?.let {
                _isLoading.value = true
                _searchResultResponse.value = try {
                    searchForDoctorsUseCase(it, query, filter)
                } catch (e: Exception) {
                    LocalError()
                }
                _isLoading.value = false
            }
        }
    }

    private val _allSpecializationsResponse: MutableStateFlow<AllSpecializationsResponse?> = MutableStateFlow(null)
    val allSpecializationsResponse = _allSpecializationsResponse.asStateFlow()

    private val _isLoadingAllSpec: MutableStateFlow<Boolean> = MutableStateFlow(false)
    private val isLoadingAllSpec = _isLoadingAllSpec.asStateFlow()

    fun getAllSpecializationsResponse() {
        viewModelScope.launch {
            if (isLoadingAllSpec.value)
                return@launch
            val token = getUserTokenAndNameUseCase()
            token.first?.let {
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

    fun resetSearchResultState() {
        _searchResultResponse.value = null
    }

    fun resetAllSpecializations() {
        _allSpecializationsResponse.value = null
    }
}