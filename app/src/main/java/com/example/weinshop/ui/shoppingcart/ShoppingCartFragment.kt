package com.example.weinshop.ui.shoppingcart

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.weinshop.MainViewModel
import com.example.weinshop.R
import com.example.weinshop.adapter.ShoppingCartAdapter
import com.example.weinshop.databinding.FragmentShoppingCartBinding

class ShoppingCartFragment : Fragment() {

    private lateinit var binding: FragmentShoppingCartBinding
    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_shopping_cart, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.shoppingCartRecyclerView.adapter =
            ShoppingCartAdapter(viewModel::addToShoppingCart, viewModel::removeFromShoppingCart)

        /**
         * Die Weinlisten und der Warenkorb werden zu einer Warenkorbliste konvertiert
         */
        viewModel.wineList.observe(viewLifecycleOwner) {
            if (viewModel.wineList.value != null && viewModel.shoppingCart.value != null) {
                viewModel.convertShoppingCartToCartList()
            }
        }

        viewModel.shoppingCart.observe(viewLifecycleOwner) {
            if (viewModel.wineList.value != null && viewModel.shoppingCart.value != null) {
                viewModel.convertShoppingCartToCartList()
            }
        }

        // Aktualisiert die Listenansicht, wenn diese sich verändert
        viewModel.shoppingCartList.observe(
            viewLifecycleOwner,
            Observer {
                (binding.shoppingCartRecyclerView.adapter as ShoppingCartAdapter).update(it)
                val endPrice = viewModel.totalPrice()
                binding.tvTotalPrice.text = "€" + String.format("%.2f", endPrice)
            }
        )

        // Navigiert beim Kauf zum Ordered Fragment und aktualisiert die Liste und den Preis
        binding.btnBuy.setOnClickListener {
            findNavController().navigate(ShoppingCartFragmentDirections.actionShoppingCartFragmentToOrderedFragment())
            viewModel.shoppingCartList.value!!.clear()
            viewModel.saveBasketToDatabase()
        }
    }
}