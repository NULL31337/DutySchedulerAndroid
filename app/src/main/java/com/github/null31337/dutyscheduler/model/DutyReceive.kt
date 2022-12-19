package com.github.null31337.dutyscheduler.model

import kotlinx.serialization.Serializable

@Serializable
data class DutyReceive(
    val id: Long,
    val name: String,
    val description: String,
    val deadline: String,
    val status: DutyStatus
)