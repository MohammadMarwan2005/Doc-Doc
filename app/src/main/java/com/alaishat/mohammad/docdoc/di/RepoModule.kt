package com.alaishat.mohammad.docdoc.di

import com.alaishat.mohammad.data.remote.APIService
import com.alaishat.mohammad.data.repoimpl.SearchForDoctorsRepoImpl
import com.alaishat.mohammad.domain.repo.SearchForDoctorsRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Created by Mohammad Al-Aishat on Aug/20/2024.
 * DocDoc Project.
 */

@Module
@InstallIn(SingletonComponent::class)
object RepoModule {
    @Provides
    @Singleton
    fun provideSearchForDoctorsRepo(apiService: APIService) : SearchForDoctorsRepo {
        return SearchForDoctorsRepoImpl(apiService)
    }
}