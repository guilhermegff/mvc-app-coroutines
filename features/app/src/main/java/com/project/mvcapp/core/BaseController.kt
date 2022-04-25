package com.project.mvcapp.core

import androidx.annotation.CallSuper

abstract class BaseController<VIEW_CONTRACT : RootViewContract> {
    protected lateinit var viewContract : VIEW_CONTRACT

    abstract fun onStart()
    abstract fun onStop()

    abstract fun initViews()
    abstract fun observeLive()

    @CallSuper
    open fun registerListener(viewContract: VIEW_CONTRACT){
        this.viewContract = viewContract
        observeLive()
    }
}