package com.alaishat.mohammad.docdoc.vm

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alaishat.mohammad.data.remote.APIService
import com.alaishat.mohammad.docdoc.R
import com.alaishat.mohammad.domain.model.login.UserLoginFailedResponse
import com.alaishat.mohammad.domain.model.login.UserLoginRequest
import com.alaishat.mohammad.domain.model.login.UserLoginResponse
import com.alaishat.mohammad.domain.model.login.UserLoginSuccessResponse
import com.alaishat.mohammad.domain.usecase.SaveUserTokenAndNameUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Mohammad Al-Aishat on Aug/20/2024.
 * DocDoc Project.
 */

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val apiService: APIService,
    private val saveUserTokenAndNameUseCase: SaveUserTokenAndNameUseCase,
    @ApplicationContext private val context: Context,
) : ViewModel() {

    private val _loginResult: MutableStateFlow<UserLoginResponse?> = MutableStateFlow(null)
    val loginResult = _loginResult.asStateFlow()

    private val _isLoading: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    fun login(userLoginRequest: UserLoginRequest) {
        viewModelScope.launch {
            if (_isLoading.value) return@launch
            _isLoading.value = true
            try {
                _loginResult.value = apiService.login(userLoginRequest)

            } catch (e: Exception) {
                _loginResult.value =
                    UserLoginFailedResponse(
                        -1, emptyList(),
                        context.getString(R.string.local_error_something_went_wrong), false
                    )
            }
            _isLoading.value = false
        }
    }

    fun resetLoginResult() {
        _loginResult.value = null
    }

    fun saveUserTokenAndName() {
        if (loginResult.value is UserLoginSuccessResponse) {
            val token = (loginResult.value as UserLoginSuccessResponse).data.token
            val username = (loginResult.value as UserLoginSuccessResponse).data.username
            viewModelScope.launch {
                saveUserTokenAndNameUseCase.invoke(token, username)
            }
        }
    }


}