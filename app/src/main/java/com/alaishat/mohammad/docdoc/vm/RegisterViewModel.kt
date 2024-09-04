package com.alaishat.mohammad.docdoc.vm

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alaishat.mohammad.data.remote.APIService
import com.alaishat.mohammad.docdoc.R
import com.alaishat.mohammad.domain.model.register.RegisterResponse
import com.alaishat.mohammad.domain.model.register.UserRegisterRequest
import com.alaishat.mohammad.domain.model.register.failureresponse.local.LocalRegisterFailureResponse
import com.alaishat.mohammad.domain.model.register.success.RegisterSuccessResponse
import com.alaishat.mohammad.domain.usecase.SaveUserTokenAndNameUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Mohammad Al-Aishat on Aug/21/2024.
 * DocDoc Project.
 */

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val apiService: APIService,
    private val saveUserTokenAndNameUseCase: SaveUserTokenAndNameUseCase,
    @ApplicationContext private val context: Context,
) : ViewModel() {

    private val _registerResult: MutableStateFlow<RegisterResponse?> = MutableStateFlow(null)
    val registerResult = _registerResult.asStateFlow()

    private val _isLoading: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    fun register(userRegisterRequest: UserRegisterRequest) {
        viewModelScope.launch {
            if (_isLoading.value) return@launch
            _isLoading.value = true
            try {
                _registerResult.value = apiService.register(userRegisterRequest)
            } catch (e: Exception) {
                _registerResult.value =
                    LocalRegisterFailureResponse(message = context.getString(R.string.local_error_something_went_wrong) + e.message)
            }
            _isLoading.value = false
        }
    }
    fun resetRegisterResult() {
        _registerResult.value = null
    }

    fun saveUserTokenAndName() {
        if (registerResult.value is RegisterSuccessResponse) {
            val token = (registerResult.value as RegisterSuccessResponse).data.token
            val username = (registerResult.value as RegisterSuccessResponse).data.username
            viewModelScope.launch {
                saveUserTokenAndNameUseCase.invoke(token, username)
            }
        }
    }


}