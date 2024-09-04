package com.alaishat.mohammad.domain.repo

import com.alaishat.mohammad.domain.model.search.DoctorSearchResultResponse

/**
 * Created by Mohammad Al-Aishat on Aug/31/2024.
 * DocDoc Project.
 */
interface SearchForDoctorsRepo {
    suspend fun searchForDoctors(token: String,  query: String): DoctorSearchResultResponse
}