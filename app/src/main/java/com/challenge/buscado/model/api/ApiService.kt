package com.challenge.buscado.model.api

import com.challenge.buscado.model.product.ResponseSearch
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("/sites/MLA/search")
    fun doSearch(@Query("q") idcliente: String?): Call<ResponseSearch?>?
}