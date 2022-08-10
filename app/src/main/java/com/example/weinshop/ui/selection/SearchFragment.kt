package com.example.weinshop.ui.selection

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
import com.example.weinshop.adapter.SearchAdapter
import com.example.weinshop.databinding.FragmentSearchBinding

class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding
    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_search, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewmodel = viewModel

        viewModel.wineList.observe(viewLifecycleOwner) { _ -> }

        viewModel.inputText.observe(
            viewLifecycleOwner,
            Observer {
                viewModel.loadResults(it)
            }
        )
        viewModel.results.observe(
            viewLifecycleOwner,
            Observer {
                binding.searchRecyclerView.adapter = SearchAdapter(it, viewModel::setCurrentArticle)
            }
        )
    }
}