package com.example.weinshop.data.models

/**
 * Diese Klasse steht für einen Warenkorbartikel mit den Eigenschaften
 * @param cartItemName Artikelname
 * @param quantity Artikelanzahl
 */

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ShoppingCart(
    @PrimaryKey(autoGenerate = true)
    val id: Int?,
    val cartItemName: String,
    var quantity: Int
)