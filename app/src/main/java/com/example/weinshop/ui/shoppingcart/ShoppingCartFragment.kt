package com.example.weinshop.ui.shoppingcart

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
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
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_shopping_cart, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        /*for (cartItem in viewModel.cartList) {
            viewModel.shoppingCartToWine(cartItem)
        }*/

        binding.shoppingCartRecyclerView.adapter =
            ShoppingCartAdapter(requireContext())


        // TODO: Wird gebraucht
        viewModel.wineList.observe(viewLifecycleOwner) {
            if (viewModel.wineList.value != null && viewModel.shoppingCart.value != null) {
                viewModel.convertShoppingCartToCartList()
            }
        }

        // TODO: Wird gebraucht
        viewModel.shoppingCart.observe(viewLifecycleOwner) {
            if (viewModel.wineList.value != null && viewModel.shoppingCart.value != null) {
                viewModel.convertShoppingCartToCartList()
            }
        }


        // TODO: Wird gebraucht
        viewModel.cartList.observe(
            viewLifecycleOwner,
            Observer { list ->
                (binding.shoppingCartRecyclerView.adapter as ShoppingCartAdapter).update(list)
            }
        )
        val endPrice = viewModel.totalPrice()
        binding.tvTotalPrice.text = String.format("%.2f", endPrice)
    }
}