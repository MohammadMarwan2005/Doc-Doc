package com.alaishat.mohammad.data.repoimpl

import android.content.SharedPreferences
import androidx.core.content.edit
import com.alaishat.mohammad.domain.repo.SharedPrefsRepo

/**
 * Created by Mohammad Al-Aishat on Aug/21/2024.
 * DocDoc Project.
 */
const val USER_TOKEN_KEY = "user_token"
const val USER_NAME_KEY = "username"
const val FIRST_VISIT_KEY = "first_visit"

class SharedPrefsRepoImpl(private val sharedPreferences: SharedPreferences) : SharedPrefsRepo {
    override suspend fun getUserTokenAndName(): Pair<String?, String?> =
        sharedPreferences.getString(USER_TOKEN_KEY, null) to sharedPreferences.getString(USER_NAME_KEY, null)

    override suspend fun saveUserTokenAndName(token: String, username: String) {
        sharedPreferences.edit {
            putString(USER_TOKEN_KEY, token)
            putString(USER_NAME_KEY, username)
        }
    }

    override suspend fun clearUserToken() = sharedPreferences.edit { remove(USER_TOKEN_KEY) }
    override suspend fun clearUserTokenAndName() {
        sharedPreferences.apply {
            edit { remove(USER_TOKEN_KEY) }
            edit { remove(USER_NAME_KEY) }
        }
    }

    override suspend fun containsUserToken(): Boolean = sharedPreferences.contains(USER_TOKEN_KEY)

    override suspend fun isFirstVisit(): Boolean = sharedPreferences.getBoolean(FIRST_VISIT_KEY, true)
    override suspend fun setFirstVisit() = sharedPreferences.edit { putBoolean(FIRST_VISIT_KEY, false) }

}