package com.project.mvcapp.ui.locationslist.usecase

import com.project.mvcapp.core.BaseUseCase
import com.project.service.model.Locations
import retrofit2.Response

interface BaseLocationsListUseCase : BaseUseCase {
    suspend fun loadLocations() : Response<Locations>
}