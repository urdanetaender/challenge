package com.challenge.buscado.view

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.challenge.buscado.R
import com.challenge.buscado.model.api.ApiService
import com.challenge.buscado.model.api.AppCliente
import com.challenge.buscado.model.ResponseSearch
import com.challenge.buscado.model.Product
import com.challenge.buscado.view.adapter.RecyclerProductAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    //Declaración del Cliente REST
    var apiService: ApiService? = null

    //Declaración de los objetos de la interfaz del activity
    var etsearch: EditText? = null
    var pb: ProgressBar? = null
    var rvProduct: RecyclerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar!!.hide()


        retrofitInit()
        findViews()
        events()
    }

    //Asignacion de eventos
    private fun events() {
        etsearch?.setOnEditorActionListener { v, actionId, event ->
            if(actionId == EditorInfo.IME_ACTION_SEARCH){
                hideKeyboard()
                val text = etsearch!!.text.toString()
                if(text.isNotEmpty()){
                    searchMP(text)
                }
                true
            } else {
                false
            }
        }
    }

    //Asignacion de Referencias
    private fun findViews() {
        etsearch = findViewById(R.id.etSearchMA)
        pb = findViewById(R.id.pbMA)
        pb?.visibility = View.GONE
        rvProduct = findViewById(R.id.rvMA)
        rvProduct?.layoutManager = LinearLayoutManager(this)
    }

    //Instanciar el Cliebte REST
    private fun retrofitInit() {
        apiService = AppCliente.getInstance()
    }

    private fun searchMP(text: String) {
        val call = apiService?.doSearch(text)

        pb?.visibility = View.VISIBLE
        call?.enqueue(object : Callback<ResponseSearch?> {
            override fun onResponse(
                call: Call<ResponseSearch?>,
                response: Response<ResponseSearch?>
            ) {
                if(response?.body()?.results?.isNotEmpty() == true){
                    val list: List<Product?> = response?.body()?.results!!
                    rvProduct!!.adapter = RecyclerProductAdapter(list)
                }
                pb?.visibility = View.GONE
            }

            override fun onFailure(call: Call<ResponseSearch?>, t: Throwable) {
                Log.d("logcat", "call failed: ${t.message}")
                pb?.visibility = View.GONE
            }
        })
    }

    fun Activity.hideKeyboard() {
        hideKeyboard(currentFocus ?: View(this))
    }

    fun Context.hideKeyboard(view: View) {
        val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }
}

