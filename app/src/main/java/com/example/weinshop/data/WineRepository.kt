package com.example.weinshop.data

/**
 * In dieser Klasse werden Informationen, wie
 * Kategorieliste
 * Weinliste,
 * Warenkorbliste,
 * Datenbank
 * und Api
 * zur Verfügung gestellt
 */

import android.content.Context
import androidx.lifecycle.LiveData
import com.example.weinshop.R
import com.example.weinshop.data.local.WineDatabase
import com.example.weinshop.data.models.Category
import com.example.weinshop.data.models.ShoppingCart
import com.example.weinshop.data.models.Wine
import com.example.weinshop.data.remote.WineApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class WineRepository(private val database: WineDatabase, private val api: WineApiService) {

    companion object {
        private var wineRepository: WineRepository? = null

        fun getInstance(context: Context): WineRepository =
            wineRepository ?: buildRepo(
                WineDatabase.getDatabase(context.applicationContext)

            ).also{
                wineRepository = it
            }

        private fun buildRepo(wineDatabase: WineDatabase): WineRepository =
            WineRepository(wineDatabase, WineApiService.api)
    }

    val cartList: LiveData<List<ShoppingCart>> = database.wineDao.getAllFromShoppingCart()

    val wineList: LiveData<List<Wine>> = database.wineDao.getAllFromWine()

    val categories = listOf(
        Category(R.drawable.redwineglass, "rot"),
        Category(R.drawable.whitewineglass, "weiß"),
        Category(R.drawable.rosewineglass, "rosé")
    )

    /**
     * Datenbankfunktionen
     */

    suspend fun getWineList() {
        withContext(Dispatchers.IO) {
            // Bugfix, damit nicht jedes mal alle Weine hinzugefügt werden
            if (database.wineDao.getCountOfWine() == 0) {
                val newWineList = WineApiService.api.getWine()
                database.wineDao.insert(newWineList)
            }
        }
    }

    suspend fun insertInCart(wine: Wine) {
        withContext(Dispatchers.IO) {
            database.wineDao.insert(
                ShoppingCart(
                    null,
                    cartItemName = wine.productName,
                    quantity = wine.cartCounter
                )
            )
        }
    }

    suspend fun  deleteAllFromShoppingCart() {
        withContext(Dispatchers.IO) {
            database.wineDao.deleteAllFromShoppingCart()
        }
    }
}