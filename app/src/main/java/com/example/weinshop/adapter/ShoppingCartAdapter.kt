package com.example.weinshop.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.weinshop.R
import com.example.weinshop.data.models.Wine

class ShoppingCartAdapter(
    private val context: Context
): RecyclerView.Adapter<ShoppingCartAdapter.ShoppingCartViewHolder>() {

    var items: List<Wine> = emptyList()

    // Klassenvariablen für den Zugriff auf die Selection Elemente
    inner class ShoppingCartViewHolder(private val view: View): RecyclerView.ViewHolder(view) {
        val ivProductImageCart: ImageView = view.findViewById(R.id.ivProduct)
        val tvProductNameCart: TextView = view.findViewById(R.id.tv_productName)
        val tvYearCart: TextView = view.findViewById(R.id.tv_year)
        val tvTasteCart: TextView = view.findViewById(R.id.tv_taste)
        val tvArticleCount: TextView = view.findViewById(R.id.tv_articleCount)
        val tvPriceCart: TextView = view.findViewById(R.id.tv_price_shoppingcart)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShoppingCartViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_shoppingcart, parent, false)

        // Verbindet das Layout mit der Klasse und gibt es zurück
        return ShoppingCartViewHolder(adapterLayout)
    }

    /* Diese Methode greift auf die Datensätze in der Liste zu mit dataset[]
     * Mit holder bekommen die TextViews die entsprechenden Daten aus der Liste zugewiesen
     */
    override fun onBindViewHolder(holder: ShoppingCartViewHolder, position: Int) {
        val shoppingCartItem = items[position]

        val imgUri = shoppingCartItem.productImg.toUri().buildUpon().scheme("http").build()

        holder.ivProductImageCart.load(imgUri) {
            error(R.drawable.arndtregent_bottle)
        }
        holder.tvProductNameCart.text = shoppingCartItem.productName
        holder.tvYearCart.text = shoppingCartItem.year
        holder.tvTasteCart.text = shoppingCartItem.taste
        holder.tvArticleCount.text = shoppingCartItem.cartCounter.toString()
        holder.tvPriceCart.text = String.format("%.2f", shoppingCartItem.price)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun update(items: List<Wine>) {
        this.items = items

        notifyDataSetChanged()
    }
}