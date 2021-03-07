package com.challenge.buscado.model.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class AppCliente {

    companion object{

        private var instance: AppCliente? = null
        private var apiService: ApiService? = null

        init {

            val okHttpClient: OkHttpClient = OkHttpClient.Builder()
                .connectTimeout(1, TimeUnit.MINUTES)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS)
                .build()

            val retrofit: Retrofit = Retrofit.Builder()
                .baseUrl("https://api.mercadolibre.com/")
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            apiService = retrofit.create(ApiService::class.java)

        }

        @JvmStatic
        fun getInstance(): ApiService? {
            if (instance == null) {
                instance = AppCliente()
            }
            return apiService
        }
    }

}