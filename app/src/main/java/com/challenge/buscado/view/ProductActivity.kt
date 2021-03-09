package com.challenge.buscado.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.challenge.buscado.R
import com.challenge.buscado.common.Constantes
import com.challenge.buscado.model.product.Product
import com.squareup.picasso.Picasso
import java.text.DecimalFormat


class ProductActivity : AppCompatActivity(), View.OnClickListener {

    //Declaración de los objetos de la interfaz del activity
    var tvvendidos: TextView? = null
    var tvtitulo: TextView? = null
    var tvprecio: TextView? = null
    var tvinfo: TextView? = null
    var tvlink: TextView? = null
    var ivprincipal: ImageView? = null

    //Declaración de la variables del activity
    private val formatea = DecimalFormat(Constantes.FORMATO_DECIMAL)
    private var link: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product)
        supportActionBar!!.hide()

        //Obtiendo la informacion desde la caja
        val product: Product = intent.getSerializableExtra("product") as Product

        findViews()
        events()
        cargarData(product);
    }

    private fun cargarData(product: Product) {
        link = product?.permalink.toString()
        if(product?.useThumbnailId == true){
            Picasso.get().load(product?.thumbnail).resize(520, 520).centerCrop().into(ivprincipal)
        }
        tvtitulo?.setText(product.title)
        tvprecio?.setText(formatea.format(product.price))
        var vendidos = "${getString(R.string.vendidos)}: ${product?.soldQuantity}"
        var detalle = " ${getString(R.string.stock)}: ${product?.availableQuantity}\n\n";
        if(product.attributes?.isNotEmpty() == true){
            product.attributes.forEach {
                if(it?.id.equals("ITEM_CONDITION")){
                    vendidos = "${it?.valueName} | $vendidos"
                }else{
                    detalle = "$detalle ${it?.name}: ${it?.valueName}\n\n";
                }
            }
        }
        tvvendidos?.setText(vendidos)
        tvinfo?.setText(detalle)
    }

    private fun findViews() {
        tvlink = findViewById(R.id.tvLinkPA)
        tvvendidos = findViewById(R.id.tvInfoVendidoPA)
        tvtitulo = findViewById(R.id.tvTituloPA)
        tvprecio = findViewById(R.id.tvPrecioPA)
        tvinfo = findViewById(R.id.tvDetallePA)
        ivprincipal = findViewById(R.id.ivPrincipalPA)
    }

    private fun events() {
        tvlink?.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        val id = v!!.id
        when (id) {
            R.id.tvLinkPA -> irMercadoLibre()
            }
        }

    private fun irMercadoLibre() {

        if (link.isNotEmpty()) {
            try {
                var parsedUrl = Uri.parse(link)
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = parsedUrl
                startActivity(intent)
            } catch (ex: Exception ) {
                ex.stackTrace
                Toast.makeText( this, R.string.no_abrir_url, Toast.LENGTH_LONG ).show()
            }
        }
    }
}

