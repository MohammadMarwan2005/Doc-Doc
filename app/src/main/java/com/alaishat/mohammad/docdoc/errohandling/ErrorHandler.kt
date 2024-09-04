package com.alaishat.mohammad.docdoc.errohandling

import com.alaishat.mohammad.domain.model.core.LocalError
import com.alaishat.mohammad.domain.model.core.UnauthorizedResponse

/**
 * Created by Mohammad Al-Aishat on Sep/02/2024.
 * DocDoc Project.
 */
class ErrorHandler {
    companion object {
        fun handle(
            response: Any?,
            onResetState: () -> Unit,
            onSnackBarHostEvent: (String) -> Unit
        ) {
            when(response) {
                is UnauthorizedResponse -> {
                    onSnackBarHostEvent(response.message)
                    onResetState()
                }
                is LocalError -> {
                    onSnackBarHostEvent(response.message)
                    onResetState()
                }
            }

        }
    }
}