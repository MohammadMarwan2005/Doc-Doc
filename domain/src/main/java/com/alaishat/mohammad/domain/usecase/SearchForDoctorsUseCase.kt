package com.alaishat.mohammad.domain.usecase

import com.alaishat.mohammad.domain.model.search.DoctorSearchResultResponse
import com.alaishat.mohammad.domain.model.search.DoctorSearchResultSuccessResponse
import com.alaishat.mohammad.domain.repo.SearchForDoctorsRepo

/**
 * Created by Mohammad Al-Aishat on Aug/31/2024.
 * DocDoc Project.
 */
class SearchForDoctorsUseCase(private val searchForDoctorsRepo: SearchForDoctorsRepo) {
    suspend operator fun invoke(
        token: String,
        query: String,
        filter: DocDocSearchFilter = UnspecifiedFilter,
    ): DoctorSearchResultResponse {

        if (filter == UnspecifiedFilter)
            return searchForDoctorsRepo.searchForDoctors(token, query)
        if (searchForDoctorsRepo.searchForDoctors(token, query) is DoctorSearchResultSuccessResponse)
            return (searchForDoctorsRepo.searchForDoctors(token, query) as DoctorSearchResultSuccessResponse).apply {
                data = data.filter { filter.acceptedIds.contains(it.specialization.id) }
            }
        return searchForDoctorsRepo.searchForDoctors(token, query)
    }
}

data class DocDocSearchFilter(val acceptedIds: List<Int>)

val UnspecifiedFilter = DocDocSearchFilter(emptyList())