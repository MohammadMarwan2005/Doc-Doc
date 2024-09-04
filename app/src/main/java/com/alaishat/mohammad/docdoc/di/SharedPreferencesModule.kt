package com.alaishat.mohammad.docdoc.di

import android.content.Context
import android.content.SharedPreferences
import com.alaishat.mohammad.data.repoimpl.SharedPrefsRepoImpl
import com.alaishat.mohammad.domain.repo.SharedPrefsRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Created by Mohammad Al-Aishat on Aug/21/2024.
 * DocDoc Project.
 */

const val SHARED_PREFERENCES_NAME = "DocDocSharedPreferences"

@Module
@InstallIn(SingletonComponent::class)
object SharedPreferencesModule {

    @Provides
    @Singleton
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)
    }

    @Provides
    @Singleton
    fun provideSharedPrefsRepo(sharedPreferences: SharedPreferences): SharedPrefsRepo =
        SharedPrefsRepoImpl(sharedPreferences)

}