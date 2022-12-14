package com.example.weinshop.data.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.weinshop.data.models.ShoppingCart
import com.example.weinshop.data.models.Wine

@Dao
interface WineDatabaseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(wine: List<Wine>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(wine: ShoppingCart)

    @Query("SELECT * FROM Wine")
    fun getAllFromWine(): LiveData<List<Wine>>

    @Query("SELECT * FROM ShoppingCart")
    fun getAllFromShoppingCart(): LiveData<List<ShoppingCart>>

    @Query("DELETE from ShoppingCart")
    fun deleteAllFromShoppingCart()

    @Query("SELECT COUNT(*) FROM Wine")
    fun getCountOfWine(): Int

}