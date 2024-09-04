package com.alaishat.mohammad.domain.usecase

import com.alaishat.mohammad.domain.repo.SharedPrefsRepo

/**
 * Created by Mohammad Al-Aishat on Sep/01/2024.
 * DocDoc Project.
 */
class ClearUserTokeAndNameUseCase(private val sharedPrefsRepo: SharedPrefsRepo) {
    suspend operator fun invoke() = sharedPrefsRepo.clearUserTokenAndName()
}