package com.project.mvcapp.ui.locationslist.usecase

import com.project.service.DataSource
import com.project.service.model.Locations
import io.reactivex.disposables.Disposable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

class LocationsListUseCase(private val dataSource: DataSource) : BaseLocationsListUseCase {

    override var disposables = HashSet<Disposable>()

    override suspend fun loadLocations() : Response<Locations> {
        return withContext(Dispatchers.Default) {
            dataSource.locations()
        }
    }
}