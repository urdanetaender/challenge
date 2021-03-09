package com.challenge.buscado.view.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.challenge.buscado.R
import com.challenge.buscado.model.product.Product
import com.challenge.buscado.common.Constantes
import com.challenge.buscado.view.ProductActivity
import com.squareup.picasso.Picasso
import java.text.DecimalFormat

class RecyclerProductAdapter(var products: List<Product?>) :
    RecyclerView.Adapter<RecyclerProductAdapter.ProductHolder>() {

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ProductHolder {
        var view: View = LayoutInflater.from(p0!!.context).inflate(
            R.layout.recycler_product_adapter,
            p0,
            false
        )
        return ProductHolder(view)
    }

    override fun getItemCount(): Int {
        return products.size ?: 0
    }

    override fun onBindViewHolder(holder: ProductHolder, position: Int) {
        var product = products?.get(position)
        holder.setDataProduct(product)
    }

    class ProductHolder(v: View) : RecyclerView.ViewHolder(v), View.OnClickListener {

        private val formatea = DecimalFormat(Constantes.FORMATO_DECIMAL)
        private var product: Product? = null
        private var imgCoupon: ImageView = v.findViewById(R.id.ivRVAP)
        private var tvTitle: TextView = v.findViewById(R.id.tvTitleRVAP)
        private var tvPrice: TextView = v.findViewById(R.id.tvPriceRVAP)
        private var tvSold: TextView = v.findViewById(R.id.tvSoldRVAP)
        private var tvQuantity: TextView = v.findViewById(R.id.tvQuantityRVAP)
        private val context = v.context

        init {
            v.setOnClickListener(this)
        }

        fun setDataProduct(product: Product?){
            this.product = product
            if(product?.useThumbnailId == true){
                Picasso.get().load(product?.thumbnail).resize(520, 520).centerCrop().into(imgCoupon)
            }
            tvTitle.setText(product?.title)
            tvPrice.setText(formatea.format(product?.price))
            tvSold.setText("${context.getString(R.string.vendidos)}: ${product?.soldQuantity}")
            tvQuantity.setText("${context.getString(R.string.disponibles)}: ${product?.availableQuantity}")

        }

        override fun onClick(v: View) {
            val context = v.context
            val showPhotoIntent = Intent(v.context, ProductActivity::class.java)
            showPhotoIntent.putExtra("product", product)
            context.startActivity(showPhotoIntent)
        }

    }
}
