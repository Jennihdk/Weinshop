package com.example.weinshop.ui.selection

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import com.example.weinshop.MainViewModel
import com.example.weinshop.R
import com.example.weinshop.adapter.CategoryAdapter
import com.example.weinshop.databinding.FragmentCategoryBinding

class CategoryFragment : Fragment() {

    private lateinit var binding: FragmentCategoryBinding
    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_category, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val recyclerView = binding.catergoryReyclerView
        recyclerView.adapter = CategoryAdapter(requireContext(), viewModel.categories)
        recyclerView.setHasFixedSize(true)
    }

}
