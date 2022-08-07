package com.example.weinshop.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Wine(@PrimaryKey(autoGenerate = true)
                var id: Long = 0,
                val productImg: String,
                val productName: String,
                val year: String,
                val taste: String,
                val price: Double,
                val description: String,
                val category: String,
                var cartCounter: Int = 0
)