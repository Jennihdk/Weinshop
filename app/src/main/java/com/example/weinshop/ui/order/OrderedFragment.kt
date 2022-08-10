package com.example.weinshop.ui.order

/**
 * In dieser Klasse ist
 */

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.example.weinshop.R
import com.example.weinshop.databinding.FragmentOrderedBinding

class OrderedFragment : Fragment() {

    private lateinit var binding: FragmentOrderedBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_ordered, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnBackToShop.setOnClickListener {
            findNavController().navigate(OrderedFragmentDirections.actionOrderedFragmentToCategoryFragment())
        }
    }
}