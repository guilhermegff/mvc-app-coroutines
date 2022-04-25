package com.project.mvcapp.ui.establishment.usecase

import com.project.service.DataSource
import com.project.service.model.Establishment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

class EstablishmentUseCase(private val dataSource: DataSource) : BaseEstablishmentUseCase {

    override suspend fun loadEstablishment(id: String) : Response<Establishment> {
        return withContext(Dispatchers.Default) {
            dataSource.establishment(id)
        }
    }
}