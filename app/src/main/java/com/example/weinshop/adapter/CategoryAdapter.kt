package com.example.weinshop.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.weinshop.R
import com.example.weinshop.data.models.Category
import com.example.weinshop.ui.selection.CategoryFragmentDirections

class CategoryAdapter(
    private val dataset: List<Category>
) : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    // der ViewHolder weiß welche Teile des Layouts beim Recycling angepasst werden
    inner class CategoryViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val tvCategoryName: TextView = view.findViewById(R.id.tv_category)
        val categoryImg: ConstraintLayout = view.findViewById(R.id.category_img)
    }

    // hier werden neue ViewHolder erstellt
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {

        // das itemLayout wird gebaut
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_category, parent, false)

        // und in einem ViewHolder zurückgegeben
        return CategoryViewHolder(adapterLayout)
    }

    // hier findet der Recyclingprozess statt
    // die vom ViewHolder bereitgestellten Parameter werden verändert
    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val categoryItem = dataset[position]
        holder.tvCategoryName.text = categoryItem.categoryName.uppercase()
        holder.categoryImg.setBackgroundResource(categoryItem.categoryImg)

        holder.categoryImg.setOnClickListener {
            holder.view.findNavController()
                .navigate(CategoryFragmentDirections.actionCategoryFragmentToSelectionFragment(
                    categoryItem.categoryName))
        }
    }

    // damit der LayoutManager weiß wie lang die Liste ist
    override fun getItemCount(): Int {
        return dataset.size
    }
}