package com.github.null31337.dutyscheduler.api

import com.github.null31337.dutyscheduler.model.DutyReceive
import com.github.null31337.dutyscheduler.model.DutySend
import com.github.null31337.dutyscheduler.model.SecretCode
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {
  @GET("/{userId}")
  suspend fun getAllDuties(
    @Path("userId") userId: Long
  ): Response<List<DutyReceive>>

  @POST("/{userId}")
  suspend fun postDuty(
    @Path("userId") userId: Long,
    @Body duty: DutySend
  ): Response<Long>

  @GET("/{userId}/generate")
  suspend fun generateCode(
    @Path("userId") userId: Long
  ): Response<SecretCode>

  @POST("login")
  suspend fun login(
    @Body secretCode: String
  ): Response<Long?>
}