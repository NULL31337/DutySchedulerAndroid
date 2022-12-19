package com.github.null31337.dutyscheduler.repository

import android.util.Log
import com.github.null31337.dutyscheduler.api.RetrofitInstance.api
import com.github.null31337.dutyscheduler.model.DutyReceive
import com.github.null31337.dutyscheduler.model.DutySend
import com.github.null31337.dutyscheduler.model.SecretCode
import retrofit2.Response

class DutyRepositoryImpl : DutyRepository {
  override suspend fun getAllDuties(userId: Long): List<DutyReceive> = whileCan { api.getAllDuties(userId) }

  override suspend fun postDuty(userId: Long, duty: DutySend): Long = whileCan { api.postDuty(userId, duty) }

  override suspend fun generateCode(userId: Long): String = whileCan { api.generateCode(userId) }.code

  override suspend fun login(code: String): Long? = api.login(code).body()

  private suspend fun <T> whileCan(block: suspend () -> Response<T>): T {
    while (true) {
      try {
        val response = block()

        with(response) {
          if (isSuccessful) {
            return body()!!
          }
        }
      } catch (e: Exception) {
        Log.d(null, e.toString())
      }
    }
  }
}