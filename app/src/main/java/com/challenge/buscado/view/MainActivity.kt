package com.challenge.buscado.view

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ProgressBar
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.challenge.buscado.R
import com.challenge.buscado.model.product.Product
import com.challenge.buscado.presenter.ProductPresenter
import com.challenge.buscado.presenter.ProductPresenterImpl
import com.challenge.buscado.view.adapter.RecyclerProductAdapter


class MainActivity : AppCompatActivity(), ProductView {

    //Declaración de los objetos de la interfaz del activity
    var etsearch: EditText? = null
    var pb: ProgressBar? = null
    var rvProduct: RecyclerView? = null

    //Prenseter
    private var productPresenter: ProductPresenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar!!.hide()

        productPresenter = ProductPresenterImpl(this)
        findViews()
        events()
        permisos()
    }

    //Asignacion de eventos
    private fun events() {
        etsearch?.setOnEditorActionListener { v, actionId, event ->
            if(actionId == EditorInfo.IME_ACTION_SEARCH){
                hideKeyboard()
                val text = etsearch!!.text.toString()
                if(text.isNotEmpty()){
                    searchProduct(text)
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

    fun Activity.hideKeyboard() {
        hideKeyboard(currentFocus ?: View(this))
    }

    fun Context.hideKeyboard(view: View) {
        val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

    override fun showProduct(products: ArrayList<Product?>) {
        pb?.visibility = View.GONE
        try {
            rvProduct!!.adapter = RecyclerProductAdapter(products)
        }catch (e: Exception){
            e.printStackTrace()
        }
    }

    override fun showProductError(msj: String) {
        pb?.visibility = View.GONE
        msjSimpleErrorDialog(msj)
    }

    override fun searchProduct(text: String) {
        pb?.visibility = View.VISIBLE
        productPresenter?.searchProduct(text)
    }

    //Solicitar permisos necesario para la aplicación
    private fun permisos() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED ) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.INTERNET), 1)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (grantResults.isEmpty()) {
            msjSimpleErrorDialog(resources.getString(R.string.msj_permisos))
        }
    }

    //Alert Dialog para mostrar mensaje de Error
    fun msjSimpleErrorDialog(msj: String) {
        var icon = R.drawable.msj_error_30
        val builder = AlertDialog.Builder(this)
        builder.setTitle(R.string.error)
            .setMessage(msj)
            .setIcon(icon)
            .setPositiveButton(R.string.ok,
                DialogInterface.OnClickListener { dialog, which -> })
        val alert = builder.create()
        alert.show()
    }
}

