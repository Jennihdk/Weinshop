package com.example.weinshop.data.remote

import com.example.weinshop.data.models.Wine
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

const val BASE_URL = "http://syntax-institut.com/public/apps/JenniferHedtke/"



interface WineApiService {

    @GET("data.json")
    suspend fun getWine(): MutableList<Wine>

    companion object {
        private val retrofit by lazy {

            val moshi = Moshi.Builder()
                .add(KotlinJsonAdapterFactory())
                .build()

            Retrofit.Builder()
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .baseUrl(BASE_URL)
                .build()
        }
        val api: WineApiService by lazy { retrofit.create(WineApiService::class.java) }
    }
}

