package com.example.testvrg.data.repository

import com.example.testvrg.data.api.RetrofitInstance
import com.example.testvrg.model.JsonReddit

class Repository {
    suspend fun getD(after: String?): JsonReddit {
        return RetrofitInstance.api.getData(after)
    }
}