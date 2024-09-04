package com.alaishat.mohammad.docdoc.di

import com.alaishat.mohammad.domain.repo.SearchForDoctorsRepo
import com.alaishat.mohammad.domain.repo.SharedPrefsRepo
import com.alaishat.mohammad.domain.usecase.ClearUserTokeAndNameUseCase
import com.alaishat.mohammad.domain.usecase.ContainsUserTokenUseCase
import com.alaishat.mohammad.domain.usecase.GetUserTokenAndNameUseCase
import com.alaishat.mohammad.domain.usecase.IsFirstVisitUseCase
import com.alaishat.mohammad.domain.usecase.SaveUserTokenAndNameUseCase
import com.alaishat.mohammad.domain.usecase.SearchForDoctorsUseCase
import com.alaishat.mohammad.domain.usecase.SetFirstVisitUseCase
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
object UseCaseModule {


    @Provides
    @Singleton
    fun provideGetUserTokenUseCase(sharedPrefsRepo: SharedPrefsRepo): GetUserTokenAndNameUseCase =
        GetUserTokenAndNameUseCase(sharedPrefsRepo)

    @Provides
    @Singleton
    fun provideSaveUserTokenUseCase(sharedPrefsRepo: SharedPrefsRepo): SaveUserTokenAndNameUseCase =
        SaveUserTokenAndNameUseCase(sharedPrefsRepo)


    @Provides
    @Singleton
    fun provideContainsUserTokenUseCase(sharedPrefsRepo: SharedPrefsRepo): ContainsUserTokenUseCase =
        ContainsUserTokenUseCase(sharedPrefsRepo)


    @Provides
    @Singleton
    fun provideIsFirstVisitUseCase(sharedPrefsRepo: SharedPrefsRepo): IsFirstVisitUseCase =
        IsFirstVisitUseCase(sharedPrefsRepo)


    @Provides
    @Singleton
    fun provideSetFirstVisitUseCase(sharedPrefsRepo: SharedPrefsRepo): SetFirstVisitUseCase =
        SetFirstVisitUseCase(sharedPrefsRepo)

    @Provides
    @Singleton
    fun provideSearchForDoctorsUseCase(searchForDoctorsRepo: SearchForDoctorsRepo): SearchForDoctorsUseCase =
        SearchForDoctorsUseCase(searchForDoctorsRepo)


    @Provides
    @Singleton
    fun provideClearUserTokeAndNameUseCase(sharedPrefsRepo: SharedPrefsRepo): ClearUserTokeAndNameUseCase =
        ClearUserTokeAndNameUseCase(sharedPrefsRepo)

}