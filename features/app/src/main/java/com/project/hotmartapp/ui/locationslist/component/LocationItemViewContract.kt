package com.project.hotmartapp.ui.locationslist.component

import com.project.hotmartapp.core.BaseListener
import com.project.hotmartapp.core.ObservableViewContract

interface LocationItemViewContract : ObservableViewContract<LocationItemViewContract.Listener> {
    interface Listener : BaseListener
    fun bind(locationViewItem: LocationViewItem)
}