package com.alaishat.mohammad.domain.model.register.failureresponse.bothtaken

import com.alaishat.mohammad.domain.model.register.failureresponse.RegisterFailureResponse

data class EmailAndPhoneTakenRegisterResponse(
    val code: Int,
    val `data`: Data,
    val message: String,
    val status: Boolean,
) : RegisterFailureResponse