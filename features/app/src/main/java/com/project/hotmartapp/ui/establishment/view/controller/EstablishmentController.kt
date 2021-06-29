package com.project.hotmartapp.ui.establishment.view.controller

import com.project.hotmartapp.core.BaseController
import com.project.hotmartapp.core.BaseSchedulerProvider
import com.project.hotmartapp.ui.establishment.usecase.EstablishmentUseCase
import timber.log.Timber

class EstablishmentController(
    private val establishmentUseCase: EstablishmentUseCase,
    private val schedulerProvider: BaseSchedulerProvider,
    private val id: String
) : BaseController<EstablishmentViewContract>(), EstablishmentViewContract.Listener {

    override fun observeLive() {
        loadEstablishment(id)
    }

    override fun registerListener(viewContract: EstablishmentViewContract) {
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

    private fun loadEstablishment(id: String) {
        establishmentUseCase.loadEstablishment(id)
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())
            .subscribe({
                when {
                    it.isSuccess -> {
                        it.getOrNull()?.let { establishment ->
                            viewContract.showEstablishment(establishment)
                        }
                    }
                    it.isFailure -> {  }
                }
            }, { Timber.e(it, "loadEstablishment: %s", it.message) })
            .run { disposables.add(this) }
    }
}