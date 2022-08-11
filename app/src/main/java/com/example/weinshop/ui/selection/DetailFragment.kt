package com.example.weinshop.ui.selection

/**
 * Diese Klasse kümmert sich um die Detailansicht des aktuellen Artikel,
 * den man ausgewählt hat
 */

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import coil.load
import com.example.weinshop.MainViewModel
import com.example.weinshop.R
import com.example.weinshop.data.models.Wine
import com.example.weinshop.databinding.FragmentDetailBinding

class DetailFragment : Fragment() {

    private lateinit var binding: FragmentDetailBinding
    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_detail, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Klassenvariable für den ausgewählten Artikel
        val wineSelected = viewModel.currentArticle.value

        // Beobachtet die Weinliste
        viewModel.wineList.observe(
            viewLifecycleOwner,
            Observer {
                if (wineSelected != null) {

                    val imgUri = wineSelected.productImg.toUri().buildUpon().scheme("http").build()
                    binding.ivProduct.load(imgUri) {
                        placeholder(R.drawable.elefantwei_burgunder_bottle)

                        if (wineSelected != null) {
                            binding.tvProductname.text = wineSelected.productName
                            binding.tvTaste.text = wineSelected.taste
                            binding.tvYear.text = wineSelected.year
                            binding.tvPrice.text = "€" + String.format("%.2f", wineSelected.price)
                            binding.tvDescription.text = wineSelected.description
                        }
                    }
                }
            }
        )

        refreshCartCounter(wineSelected!!)

        // clickListener, um den ausgewählten Artikel zum Warenkorb hinzuzufügen
        binding.ibAddShoppingCart.setOnClickListener {
            viewModel.addToShoppingCart(wineSelected)
            refreshCartCounter(wineSelected)
        }

        // clickListener, um den ausgewählten Artikel aus dem Warenkorb zu entfernen
        binding.ibRemoveShoppingCart.setOnClickListener {
            viewModel.removeFromShoppingCart(wineSelected)
            refreshCartCounter(wineSelected)
        }
    }

    // Diese Funktion, sorgt dafür, dass der Counter im Detailscreen und Warenkorbscreen gleich sind
    fun refreshCartCounter(wineSelected: Wine) {
        for (wine in viewModel.shoppingCartList.value!!) {
            if (wine.productName == wineSelected.productName) {
                binding.tvArticleCounter.text = wine.cartCounter.toString()
                return
            }
        }
        binding.tvArticleCounter.text = "0"
    }
}