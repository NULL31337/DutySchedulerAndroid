package com.github.null31337.dutyscheduler.repository

import com.github.null31337.dutyscheduler.model.DutyReceive
import com.github.null31337.dutyscheduler.model.DutySend

interface DutyRepository {
  suspend fun getAllDuties(userId: Long): List<DutyReceive>
  suspend fun postDuty(userId: Long, duty: DutySend): Long
  suspend fun generateCode(userId: Long): String
  suspend fun login(code: String): Long?
}