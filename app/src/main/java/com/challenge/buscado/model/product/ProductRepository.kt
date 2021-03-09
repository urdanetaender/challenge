package com.challenge.buscado.model.product

interface ProductRepository {

    fun getSearchProductAPI(text: String)
}