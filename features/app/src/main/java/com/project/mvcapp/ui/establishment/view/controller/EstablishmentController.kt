package com.project.mvcapp.ui.establishment.view.controller

import com.project.mvcapp.core.BaseController
import com.project.mvcapp.core.BaseSchedulerProvider
import com.project.mvcapp.core.BaseScreenNavigator
import com.project.mvcapp.ui.establishment.component.reviews.ReviewViewItem
import com.project.mvcapp.ui.establishment.ext.toEstablishmentViewItem
import com.project.mvcapp.ui.establishment.usecase.BaseEstablishmentUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EstablishmentController(
    private val establishmentUseCase: BaseEstablishmentUseCase,
    private val schedulerProvider: BaseSchedulerProvider,
    private val id: String,
    private val screenNavigator: BaseScreenNavigator
) : BaseController<EstablishmentViewContract>(), EstablishmentViewContract.Listener {

    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    val reviews by lazy { createReviewItems() }

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
        clearDisposables()
    }

    override fun initViews() {
        viewContract.bindBackButton()
        viewContract.bindReviewsButton()
    }

    private fun loadEstablishment(id: String) {
        coroutineScope.launch {
            viewContract.showLoading()
            try {
                establishmentUseCase.loadEstablishment(id).let {
                    viewContract.hideLoading()
                    when(it.isSuccessful) {
                        true -> {
                            it.body()?.let { establishment ->
                                establishment.toEstablishmentViewItem()
                                    .let { establishmentViewItem ->
                                        viewContract.showEstablishment(establishmentViewItem)
                                        viewContract.showContacts(establishmentViewItem)
                                        viewContract.showSchedule(establishmentViewItem)
                                    }
                                reviews.shuffle()
                                reviews.filterIndexed { index, _ -> index < 3 }
                                    .let { threeReviews ->
                                        viewContract.showReviews(threeReviews as ArrayList<ReviewViewItem>)
                                    }
                                viewContract.showReviewsCount(reviews.size)
                                viewContract.showPictures()
                            }
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

    override fun onMoreReviewsClick() {
        viewContract.showAllReviews(reviews)
    }

    override fun onBackButtonClick() {
        screenNavigator.toLastScreen()
    }

    override fun onErrorViewClick() {
        loadEstablishment(id)
    }

    private fun createReviewItems() : ArrayList<ReviewViewItem> {
        return arrayListOf(
            ReviewViewItem("", 4.0, "Bom", "aaaa aaaaaa aaaaa aaaa aaaaaa aaaaaa aaaaaa aaa aaaaaaaa aaaaa aaaaa aaaa aaa ", "José, São Paulo - SP"),
            ReviewViewItem("", 5.0, "Muito bom", "", "Maria, Belo Horizonte - MG"),
            ReviewViewItem("", 5.0, "Ótimo", "", ""),
            ReviewViewItem("", 4.5, "Gostei", "b bbb bbbb bbbbb bbbbbb bbbbb bbb bb bbbbb bbbb bbbb bb b", "Manoel, Rio de Janeiro - RJ"),
            ReviewViewItem("", 3.0, "Normal", "", "Francisco, São Paulo - SP"),
            ReviewViewItem("", 3.5, "Eu volto", "cccc cc cc c cc ccc c cc cc cc c ", "Fernanda, Salvador - BA"),
            ReviewViewItem("", 4.2, "Recomendo", "dd dd d dddd dd dd d d dd ddd ddddd d d dd d dd d  dd d d d d dd dd ddd ddd d d d dd d ", ""),
            ReviewViewItem("", 5.0, "Bom demais da conta", "", ""),
            ReviewViewItem("", 2.0, "", "", ""),
            ReviewViewItem("", 2.5, "", "", ""),
        )
    }
}