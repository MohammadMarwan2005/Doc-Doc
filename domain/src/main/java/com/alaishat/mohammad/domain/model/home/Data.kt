package com.alaishat.mohammad.domain.model.home

import com.alaishat.mohammad.domain.model.core.Doctor

data class Data(
    val doctors: List<Doctor>,
    val id: Int,
    val name: String
)