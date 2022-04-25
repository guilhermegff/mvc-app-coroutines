package com.project.mvcapp.ui.establishment.usecase

import com.project.mvcapp.core.BaseUseCase
import com.project.service.model.Establishment
import retrofit2.Response

interface BaseEstablishmentUseCase : BaseUseCase {
    suspend fun loadEstablishment(id: String) : Response<Establishment>
}