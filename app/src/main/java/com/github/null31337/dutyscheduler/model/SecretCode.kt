package com.github.null31337.dutyscheduler.model

import kotlinx.serialization.Serializable

@Serializable
data class SecretCode(
  val code: String
)