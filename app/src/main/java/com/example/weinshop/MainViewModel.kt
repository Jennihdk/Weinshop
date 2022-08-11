package com.example.weinshop

/**
 * Im Viewmodel befindet sich die Logik der App
 */

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.weinshop.data.WineRepository
import com.example.weinshop.data.models.Wine
import kotlinx.coroutines.launch
import java.lang.Exception

const val TAG = "MainViewModel"

enum class ApiStatus { LOADING, ERROR, DONE }

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = WineRepository.getInstance(application)

    var inputText = MutableLiveData<String>()

    // Die Listen werden in einer Variablen gespeichert
    var wineList = repository.wineList
    val categories = repository.categories
    val results = MutableLiveData<MutableList<Wine>>()

    // Hier werden die Rohdaten von der Datenbank gespeichert
    val shoppingCart = repository.cartList

    // In der cartList wird der aktuelle Warenkorb gespeichert
    var shoppingCartList = MutableLiveData<MutableList<Wine>>()

    // Diese Variable speichert den aktuellen Artikel
    private val _currentArticle = MutableLiveData<Wine>(wineList.value?.get(0))
    val currentArticle: LiveData<Wine>
        get() = _currentArticle

    // Diese Variable speichert den Api Status
    private val _loading = MutableLiveData<ApiStatus>()
    val loading: LiveData<ApiStatus>
        get() = _loading

    init {
        loadData()
        results.value = mutableListOf()
        shoppingCartList.value = mutableListOf()
    }

    fun loadData() {
        viewModelScope.launch {
            _loading.value = ApiStatus.LOADING
            try {
                repository.getWineList()
                _loading.value = ApiStatus.DONE
            } catch (e: Exception) {
                Log.e(TAG, "Error loading Data $e")
                if (wineList.value.isNullOrEmpty()) {
                    _loading.value = ApiStatus.ERROR
                } else {
                    _loading.value = ApiStatus.DONE
                }
            }
        }
    }

    // Diese Funktion zeigt die Suchergebnisse
    fun loadResults(search: String) {
        val newResults = mutableListOf<Wine>()

        viewModelScope.launch {
            results.value!!.clear()
            for (wine in wineList.value!!) {
                if (wine.productName.lowercase().contains(search.lowercase())) {
                    newResults.add(wine)
                }
            }
            results.value = newResults
        }
    }

    fun setCurrentArticle(wine: Wine) {
        _currentArticle.value = wine
    }

    // Diese Funktion fügt einen Artikel zum Warenkorb hinzu
    fun addToShoppingCart(wineSelected: Wine) {
        var foundInBasket = false

        for (basketItem in shoppingCartList.value!!) {
            if (basketItem.productName == wineSelected.productName) {
                basketItem.cartCounter += 1
                foundInBasket = true
            }
        }
        if (!foundInBasket) {
            wineSelected.cartCounter = 1
            shoppingCartList.value!!.add(wineSelected)
        }

        viewModelScope.launch {
            saveBasketToDatabase()
        }
    }

    // Diese Funktion löscht einen Artikel aus dem Warenkorb
    fun removeFromShoppingCart(wineSelected: Wine) {
        for (basketItem in shoppingCartList.value!!) {
            if (basketItem.productName == wineSelected.productName) {
                if (basketItem.cartCounter == 1) {
                    shoppingCartList.value!!.remove(basketItem)
                    break
                } else {
                    basketItem.cartCounter -= 1
                }
            }
        }
        saveBasketToDatabase()
    }

    // Diese Funtion speichert den aktuellen Warenkorb Zustand
    fun saveBasketToDatabase() {
        viewModelScope.launch {
            repository.deleteAllFromShoppingCart()

            for (basketItem in shoppingCartList.value!!) {
                repository.insertInCart(basketItem)
            }
        }
    }

    // Hier wird die Liste der Items im Warenkorb von der DB durchgegangen und konvertiert
    fun convertShoppingCartToCartList() {
        val newWineList = mutableListOf<Wine>()

        for (shoppingCartItem in shoppingCart.value!!) {
            for (wine in wineList.value!!) {
                if (wine.productName == shoppingCartItem.cartItemName) {
                    wine.cartCounter = shoppingCartItem.quantity

                    if (!newWineList.contains(wine)) {
                        newWineList.add(wine)
                    }
                }
            }
        }

        shoppingCartList.value = newWineList
    }

    // Diese Funktion rechnet den Gesamtpreis im Warenkorb zusammen
    fun totalPrice(): Double {
        return shoppingCartList.value!!.sumOf { it.price * it.cartCounter }
    }
}