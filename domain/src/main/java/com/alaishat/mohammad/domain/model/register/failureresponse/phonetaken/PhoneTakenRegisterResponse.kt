package com.alaishat.mohammad.domain.model.register.failureresponse.phonetaken

import com.alaishat.mohammad.domain.model.register.failureresponse.RegisterFailureResponse

data class PhoneTakenRegisterResponse(
    val code: Int,
    val `data`: Data,
    val message: String,
    val status: Boolean
): RegisterFailureResponse