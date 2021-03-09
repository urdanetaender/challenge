package com.challenge.buscado.model.product

import android.util.Log
import com.challenge.buscado.model.api.ApiService
import com.challenge.buscado.model.api.AppCliente
import com.challenge.buscado.presenter.ProductPresenter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductRepositoryImpl(var productPresenter: ProductPresenter): ProductRepository {

    override fun getSearchProductAPI(text: String) {

        var apiService: ApiService? = AppCliente.getInstance()
        val call = apiService?.doSearch(text)

        call?.enqueue(object : Callback<ResponseSearch?> {
            override fun onResponse(
                call: Call<ResponseSearch?>,
                response: Response<ResponseSearch?>
            ) {
                if(response?.body()?.results?.isNotEmpty() == true){
                    val list: ArrayList<Product?> = (response?.body()?.results as ArrayList<Product?>?)!!
                    productPresenter.showProduct(list)
                }else{
                    productPresenter.showProductError("Producto: ${text}, no encontrado")
                }
            }

            override fun onFailure(call: Call<ResponseSearch?>, t: Throwable) {
                Log.d("onFailure", "call failed: ${t.message}")
                t.stackTrace
                productPresenter.showProductError("Error al intentar buscar los productos: ${t.message}")
            }
        })
    }
}