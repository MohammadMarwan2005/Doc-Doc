package com.alaishat.mohammad.docdoc.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alaishat.mohammad.data.remote.APIService
import com.alaishat.mohammad.domain.model.core.LocalError
import com.alaishat.mohammad.domain.model.show_profile.UserProfileResponse
import com.alaishat.mohammad.domain.usecase.GetUserTokenAndNameUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Mohammad Al-Aishat on Sep/01/2024.
 * DocDoc Project.
 */

@HiltViewModel
class UserProfileViewModel @Inject constructor(
    private val getUserTokenAndNameUseCase: GetUserTokenAndNameUseCase,
    private val apiService: APIService,
) : ViewModel() {

    private val _userProfileResponse: MutableStateFlow<UserProfileResponse?> = MutableStateFlow(null)
    val userProfileResponse = _userProfileResponse.asStateFlow()

    private val _isLoading: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()


    init {
        getUserProfile()
    }

    private fun getUserProfile() {
        viewModelScope.launch {
            if (isLoading.value) return@launch
            val token = getUserTokenAndNameUseCase()
            token.first?.let {
                _isLoading.value = true
                _userProfileResponse.value = try {
                    apiService.getUserProfile(token = it)
                } catch (e: Exception) {
                    LocalError()
                }
                _isLoading.value = false
            }
        }
    }

    fun resetProfileResponse() {
        _userProfileResponse.value = null
    }


}