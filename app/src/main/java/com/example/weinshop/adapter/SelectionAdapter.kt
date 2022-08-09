package com.example.weinshop.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.weinshop.R
import com.example.weinshop.data.models.Wine
import com.example.weinshop.ui.selection.SelectionFragmentDirections

class SelectionAdapter(
    private val context: Context,
    private val setCurrentArticle: (Wine) -> Unit
): RecyclerView.Adapter<SelectionAdapter.SelectionViewHolder>() {

    private var dataset = emptyList<Wine>()

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(list: List<Wine>) {
        dataset = list
        notifyDataSetChanged()
    }

    // Klassenvariablen für den Zugriff auf die Selection Elemente
    inner class SelectionViewHolder(val view: View): RecyclerView.ViewHolder(view) {
        val ivProductImage: ImageView = view.findViewById(R.id.ivProduct)
        val tvProductName: TextView = view.findViewById(R.id.tv_productName)
        val tvYear: TextView = view.findViewById(R.id.tv_year)
        val tvTaste: TextView = view.findViewById(R.id.tv_taste)
        val tvPrice: TextView = view.findViewById(R.id.tv_price)
        val btnProduct: Button = view.findViewById(R.id.btn_product)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SelectionViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_selection, parent, false)

        // Verbindet das Layout mit der Klasse und gibt es zurück
        return SelectionViewHolder(adapterLayout)
    }

    /* Diese Methode greift auf die Datensätze in der Liste zu mit dataset[]
     * Mit holder bekommen die TextViews die entsprechenden Daten aus der Liste zugewiesen
     */
    override fun onBindViewHolder(holder: SelectionViewHolder, position: Int) {
        val selectionItem = dataset[position]

        val imgUri = selectionItem.productImg.toUri().buildUpon().scheme("http").build()

        holder.ivProductImage.load(imgUri)
        holder.tvProductName.text = selectionItem.productName
        holder.tvYear.text = selectionItem.year
        holder.tvTaste.text = selectionItem.taste
        holder.tvPrice.text = "€" + String.format("%.2f", selectionItem.price)

        // Navigation von SelectionFragment zum Detailfragment beim Klick auf den "Zum Produkt" Button
        holder.btnProduct.setOnClickListener {
            setCurrentArticle(selectionItem)
            val navController = holder.view.findNavController()
            navController.navigate(SelectionFragmentDirections.actionSelectionFragmentToDetailFragment(
                selectionItem.id))
        }
    }

    override fun getItemCount(): Int {
        return dataset.size
    }
}