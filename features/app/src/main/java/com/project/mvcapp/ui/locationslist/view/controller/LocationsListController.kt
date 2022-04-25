package com.project.mvcapp.ui.locationslist.view.controller

import com.project.mvcapp.core.BaseController
import com.project.mvcapp.core.BaseSchedulerProvider
import com.project.mvcapp.core.BaseScreenNavigator
import com.project.mvcapp.ui.locationslist.component.LocationViewItem
import com.project.mvcapp.ui.locationslist.component.LocationsAdapterListener
import com.project.mvcapp.ui.locationslist.ext.toLocationViewItem
import com.project.mvcapp.ui.locationslist.usecase.BaseLocationsListUseCase
import com.project.service.model.Locations
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LocationsListController(private val locationsListUseCase: BaseLocationsListUseCase,
                              private val schedulerProvider: BaseSchedulerProvider,
                              private val screenNavigator: BaseScreenNavigator
) : BaseController<LocationsListViewContract>(), LocationsListViewContract.Listener, LocationsAdapterListener {

    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    override fun observeLive() {
        loadLocations()
    }

    override fun registerListener(viewContract: LocationsListViewContract) {
        super.registerListener(viewContract)
        this.viewContract.registerListener(this)
    }

    override fun onStart() {
        viewContract.registerListener(this)
    }

    override fun onStop() {
        viewContract.unregisterListener(this)
    }

    override fun initViews() {
        //TODO("Not yet implemented")
    }

    private fun loadLocations() {
        coroutineScope.launch {
            viewContract.showLoading()
            try {
                locationsListUseCase.loadLocations().let {
                    viewContract.hideLoading()
                    when (it.isSuccessful) {
                        true -> {
                            val locations = it.body() ?: Locations(arrayListOf())
                            locations.collection
                                .map { location -> location.toLocationViewItem() }
                                .let { newCollection -> viewContract.showLocations(newCollection as ArrayList<LocationViewItem>) }
                        }
                        false -> {
                            viewContract.showError()
                        }
                    }
                }
            } catch (e: Exception) {
                viewContract.hideLoading()
                viewContract.showError()
            }
        }
    }

    override fun onErrorViewClick() {
        loadLocations()
    }

    override fun onLocationClick(locationViewItem: LocationViewItem) {
        screenNavigator.toDetailScreen(locationViewItem)
    }
}