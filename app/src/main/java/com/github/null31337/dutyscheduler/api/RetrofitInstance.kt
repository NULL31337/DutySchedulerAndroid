package com.github.null31337.dutyscheduler.api

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit

object RetrofitInstance {
  private val json = Json {
    ignoreUnknownKeys = true
  }

  private val client = OkHttpClient().newBuilder()
    .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
    .build()

  @OptIn(ExperimentalSerializationApi::class)
  private val retrofit by lazy {
    Retrofit.Builder().baseUrl("http://89.208.86.234:80")
      .client(client)
      .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
      .build()
  }

  val api: ApiService by lazy {
    retrofit.create(ApiService::class.java)
  }
}