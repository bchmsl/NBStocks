package com.nbstocks.nbstocks.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CompanyListingEntity(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    val symbol: String?,
    val name: String?,
    val exchange: String?,
    val currency: String?,
    val country: String?,
    val type: String?
)