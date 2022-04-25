package com.project.service

import com.project.service.model.Establishment
import com.project.service.model.Locations
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface Service {
    @GET("/locations")
    suspend fun locations() : Response<Locations>

    @GET("/locations/{id}")
    suspend fun establishment(@Path("id") id: String) : Response<Establishment>
}