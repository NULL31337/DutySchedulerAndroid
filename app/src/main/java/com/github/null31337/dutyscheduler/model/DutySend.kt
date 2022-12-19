package com.github.null31337.dutyscheduler.model

import kotlinx.serialization.Serializable

@Serializable
data class DutySend(
    val name: String,
    val description: String,
    val deadline: String,
    val status: DutyStatus
)