package com.challenge.buscado.model.product

import com.challenge.buscado.presenter.ProductPresenter

class ProductInteractorImpl(productPresenter: ProductPresenter): ProductInteractor {

    private var productRepository: ProductRepository = ProductRepositoryImpl(productPresenter)

    override fun getSearchProductAPI(text: String) {
        productRepository.getSearchProductAPI(text)
    }

}