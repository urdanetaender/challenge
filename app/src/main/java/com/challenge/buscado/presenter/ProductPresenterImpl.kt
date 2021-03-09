package com.challenge.buscado.presenter

import com.challenge.buscado.model.product.Product
import com.challenge.buscado.model.product.ProductInteractor
import com.challenge.buscado.model.product.ProductInteractorImpl
import com.challenge.buscado.view.ProductView

class ProductPresenterImpl(var productView: ProductView): ProductPresenter {

    private var productInteractor: ProductInteractor = ProductInteractorImpl(this)

    override fun showProduct(products: ArrayList<Product?>) {
        productView.showProduct(products)
    }

    override fun showProductError(msj: String) {
        productView.showProductError(msj)
    }

    override fun searchProduct(text: String) {
        productInteractor.getSearchProductAPI(text)
    }
}