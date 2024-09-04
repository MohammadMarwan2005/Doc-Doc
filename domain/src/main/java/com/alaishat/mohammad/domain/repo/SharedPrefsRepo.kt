package com.alaishat.mohammad.domain.repo

/**
 * Created by Mohammad Al-Aishat on Aug/21/2024.
 * DocDoc Project.
 */
interface SharedPrefsRepo {

    suspend fun getUserTokenAndName() : Pair<String?, String?>

    suspend fun saveUserTokenAndName(token: String, username: String)

    suspend fun clearUserToken()

    suspend fun clearUserTokenAndName()

    suspend fun containsUserToken() : Boolean

    suspend fun isFirstVisit() : Boolean

    suspend fun setFirstVisit()


}