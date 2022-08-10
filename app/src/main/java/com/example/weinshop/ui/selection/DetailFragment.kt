package com.example.weinshop.ui.selection

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
        val wineSelected = viewModel.currentArticle.value

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

        binding.ibAddShoppingCart.setOnClickListener {
            viewModel.addToShoppingCart(wineSelected)
            refreshCartCounter(wineSelected)
        }

        binding.ibRemoveShoppingCart.setOnClickListener {
            viewModel.removeFromShoppingCart(wineSelected)
            refreshCartCounter(wineSelected)
        }
    }

    fun refreshCartCounter(wineSelected: Wine) {
        for (wine in viewModel.shoppingCartList) {
            if (wine.productName == wineSelected.productName) {
                binding.tvArticleCounter.text = wine.cartCounter.toString()
                return
            }
        }
        binding.tvArticleCounter.text = "0"
    }
}