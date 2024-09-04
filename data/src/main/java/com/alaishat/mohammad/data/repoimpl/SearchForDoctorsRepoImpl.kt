package com.alaishat.mohammad.data.repoimpl

import com.alaishat.mohammad.data.remote.APIService
import com.alaishat.mohammad.domain.model.search.DoctorSearchResultResponse
import com.alaishat.mohammad.domain.repo.SearchForDoctorsRepo

/**
 * Created by Mohammad Al-Aishat on Aug/31/2024.
 * DocDoc Project.
 */
class SearchForDoctorsRepoImpl(private val apiService: APIService) : SearchForDoctorsRepo {
    override suspend fun searchForDoctors(token: String, query: String): DoctorSearchResultResponse = apiService.searchForDoctors(token, query)
}