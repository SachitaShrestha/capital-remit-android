package com.webtecsolutions.capitalremit.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Transaction(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
)