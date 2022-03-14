package com.application.shoprye.models

import androidx.room.*
import java.util.*

@Entity(
    tableName = "shopping_cart_entries",
    foreignKeys = [
        ForeignKey(entity = RyeJob::class, parentColumns = ["id"], childColumns = ["rye_job_id"])
    ],
    indices = [Index("rye_job_id")]
)
data class ShoppingCartEntry(
    @ColumnInfo(name = "rye_job_id") val ryeJobId: Int,
    @ColumnInfo(name = "added_to_cart_date") val addedToCartDate: Calendar = Calendar.getInstance()
) {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var shoppingCartEntryId: Long = 0
}