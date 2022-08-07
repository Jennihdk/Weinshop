package com.example.weinshop.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.weinshop.R
import com.example.weinshop.data.models.Wine

class SearchAdapter(
    private val dataset: List<Wine>
): RecyclerView.Adapter<SearchAdapter.SearchViewHolder>() {

    // Klassenvariablen für den Zugriff auf die Selection Elemente
    inner class SearchViewHolder(private val view: View): RecyclerView.ViewHolder(view) {
        val tvProductNameSearch: TextView = view.findViewById(R.id.tv_productName)
        val tvTasteSearch: TextView = view.findViewById(R.id.tv_taste)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_search, parent, false)

        // Verbindet das Layout mit der Klasse und gibt es zurück
        return SearchViewHolder(adapterLayout)
    }

    /* Diese Methode greift auf die Datensätze in der Liste zu mit dataset[]
     * Mit holder bekommen die TextViews die entsprechenden Daten aus der Liste zugewiesen
     */
    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        val searchItem = dataset[position]
        holder.tvProductNameSearch.text = searchItem.productName
        holder.tvTasteSearch.text = searchItem.taste
    }

    override fun getItemCount(): Int {
        return dataset.size
    }
}