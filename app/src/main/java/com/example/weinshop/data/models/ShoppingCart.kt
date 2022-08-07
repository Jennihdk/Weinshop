package com.example.weinshop.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ShoppingCart(
    @PrimaryKey(autoGenerate = true)
    val id: Int?,
    val cartItemName: String,
    var quantity: Int
)