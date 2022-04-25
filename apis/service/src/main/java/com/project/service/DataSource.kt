package com.project.service

import com.project.service.model.Establishment
import com.project.service.model.Locations
import retrofit2.Response

interface DataSource {
    suspend fun locations() : Response<Locations>
    suspend fun establishment(id: String): Response<Establishment>
}