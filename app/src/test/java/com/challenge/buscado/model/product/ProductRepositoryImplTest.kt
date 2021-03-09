package com.challenge.buscado.model.product

import com.challenge.buscado.presenter.ProductPresenter
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class ProductRepositoryImplTest {
    @Mock
    private val presenter: ProductPresenter? = null

    private var repository: ProductRepositoryImpl? = null

    @Before
    @Throws(Exception::class)
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        repository = ProductRepositoryImpl(presenter!!)
    }

    @Test
    @Throws(Exception::class)
    fun valGetSearchProductAPI_showProductError() {
        repository!!.getSearchProductAPI(anyString())
        Mockito.verify(presenter)?.showProductError(ArgumentMatchers.anyString())
    }
}
