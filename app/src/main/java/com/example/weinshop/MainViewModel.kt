package com.example.weinshop

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

    /** Hier werden die Rohdaten von der DB gespeichert */
    // TODO: Wird gebraucht
    val shoppingCart = repository.cartList

    /** Hier werden die Weine die im Warenkorb liegen mit Hilfe von shoppingCart gespeichert */
    // TODO: Wird gebraucht
    val cartList = MutableLiveData<List<Wine>>()

    var shoppingCartList = mutableListOf<Wine>()

    private val _currentArticle = MutableLiveData<Wine>(wineList.value?.get(0))
    val currentArticle: LiveData<Wine>
        get() = _currentArticle

    private val _loading = MutableLiveData<ApiStatus>()
    val loading: LiveData<ApiStatus>
        get() = _loading

    init {
        loadData()
        results.value = mutableListOf()
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

    fun addToShoppingCart(wineSelected: Wine) {
        var foundInBasket = false

        for (basketItem in shoppingCartList) {
            if (basketItem.productName == wineSelected.productName) {
                basketItem.cartCounter += 1
                foundInBasket = true
            }
        }
        if (!foundInBasket) {
            val newWine = wineSelected
            newWine.cartCounter = 1
            shoppingCartList.add(newWine)
        }

        viewModelScope.launch {
            saveBasketToDatabase()
        }
    }

    fun removeFromShoppingCart(wineSelected: Wine) {
        for (basketItem in shoppingCartList) {
            if (basketItem.productName == wineSelected.productName) {
                if (basketItem.cartCounter == 1) {
                    shoppingCartList.remove(basketItem)
                    break
                } else {
                    basketItem.cartCounter -= 1
                }
            }
        }
        saveBasketToDatabase()
    }

    fun saveBasketToDatabase() {
        viewModelScope.launch {
            repository.deleteAllFromShoppingCart()

            for (basketItem in shoppingCartList) {
                repository.insertInCart(basketItem)
            }
        }
    }

    /**
     * Hier wird die Liste der Items im Warenkorb von der DB durchgegangen und konvertiert
     */
    // TODO: Wird gebraucht
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

        cartList.value = newWineList
    }

    fun totalPrice(): Double {
        return shoppingCartList.sumOf { it.price * it.cartCounter }
    }
}