package com.alaishat.mohammad.domain.usecase

import com.alaishat.mohammad.domain.repo.SharedPrefsRepo

/**
 * Created by Mohammad Al-Aishat on Aug/21/2024.
 * DocDoc Project.
 */
class SetFirstVisitUseCase(private val sharedPrefsRepo: SharedPrefsRepo) {
    suspend operator fun invoke() {sharedPrefsRepo.setFirstVisit()}
}