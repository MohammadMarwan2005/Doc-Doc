package com.alaishat.mohammad.domain.model.register.failureresponse.emailtaken

import com.alaishat.mohammad.domain.model.register.failureresponse.RegisterFailureResponse

data class EmailTakenRegisterResponse(
    val code: Int,
    val `data`: Data,
    val message: String,
    val status: Boolean
) : RegisterFailureResponse