package com.application.shoprye.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "rye_jobs")
data class RyeJob(
    @PrimaryKey @ColumnInfo(name = "id") val id: Int,
    var name: String,
    val description: String,
    val imageUrl: String = "http://theryebread.com/Content/no_image_available.jpg",
    val requiresTravel: Boolean = false,
    val cost: String = "$98,765.43"
) {
    override fun toString() = name
}