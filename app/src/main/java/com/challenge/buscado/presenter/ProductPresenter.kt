package com.challenge.buscado.presenter

import com.challenge.buscado.model.product.Product

interface ProductPresenter {
    //Vista
    fun showProduct(products: ArrayList<Product?>)
    fun showProductError(msj: String)

    //Interactor
    fun searchProduct(text: String)
}