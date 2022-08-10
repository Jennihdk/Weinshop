package com.example.weinshop.data.models

/**
 * Diese Klasse steht für einen Wein, der jeweils verschiedene Eigenschaften hat
 *
 * @param productImg Foto
 * @param productName Name des Weines
 * @param year Jahrgang
 * @param taste Geschmacksrichtung
 * @param price Preis
 * @param description Beschreibung
 * @param category Kategorie
 */

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