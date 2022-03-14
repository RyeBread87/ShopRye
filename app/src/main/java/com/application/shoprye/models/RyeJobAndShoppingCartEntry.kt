package com.application.shoprye.models

import androidx.room.Embedded
import androidx.room.Relation

data class RyeJobAndShoppingCartEntry (
    @Embedded
    val ryeJob: RyeJob,

    @Relation(parentColumn = "id", entityColumn = "rye_job_id")
    var shoppingCartEntryList: List<ShoppingCartEntry> = emptyList()
)