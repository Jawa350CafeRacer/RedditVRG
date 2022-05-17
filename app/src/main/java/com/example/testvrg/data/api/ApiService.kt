package com.example.testvrg.data.api


import com.example.testvrg.model.JsonReddit
import retrofit2.http.*

interface ApiService {

    @GET("top.json")
    suspend fun getData(
        @Query("after") after: String?,
        @Query("limit") limit: String = "25"
    ): JsonReddit
}