package com.application.shoprye.viewmodels

import androidx.lifecycle.ViewModel
import com.application.shoprye.models.RyeJobAndShoppingCartEntry
import java.text.SimpleDateFormat
import java.util.*

class RyeJobAndShoppingCartEntryViewModel(ryeJobAndShoppingCartEntry: RyeJobAndShoppingCartEntry) : ViewModel() {
    private val ryeJob = checkNotNull(ryeJobAndShoppingCartEntry.ryeJob)
    private var shoppingCartEntry = ryeJobAndShoppingCartEntry.shoppingCartEntryList[0]

    val addedToCartDate: String = dateFormat.format(shoppingCartEntry.addedToCartDate.time)
    val imageUrl
        get() = ryeJob.imageUrl
    val ryeJobName
        get() = ryeJob.name
    val ryeJobId
        get() = ryeJob.id

    companion object {
        private val dateFormat = SimpleDateFormat("MMM d, yyyy", Locale.US)
    }
}
