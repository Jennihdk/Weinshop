package com.example.weinshop.ui.selection

/**
 * Diese Klasse kümmert sich um die Weinlisten der jeweiligen Kategorien
 * Rotweinliste, Weißweinliste und Roséweinliste
 */

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.example.weinshop.ApiStatus
import com.example.weinshop.MainViewModel
import com.example.weinshop.R
import com.example.weinshop.adapter.SelectionAdapter
import com.example.weinshop.data.models.Wine
import com.example.weinshop.databinding.FragmentSelectionBinding

class SelectionFragment : Fragment() {

    private lateinit var binding: FragmentSelectionBinding
    private val viewModel: MainViewModel by activityViewModels()
    private var category: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            category = it.getString("category")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_selection, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lateinit var wineList: List<Wine>

        val selectionAdapter = SelectionAdapter(viewModel::setCurrentArticle)
        binding.selectionRecyclerView.adapter = selectionAdapter

        viewModel.loading.observe(
            viewLifecycleOwner
        ) {
            when (it) {
                ApiStatus.LOADING -> binding.progressBar.visibility = View.VISIBLE
                ApiStatus.ERROR -> {
                    binding.progressBar.visibility = View.GONE
                    binding.internetError.visibility = View.VISIBLE
                }
                else -> {
                    binding.progressBar.visibility = View.GONE
                    binding.internetError.visibility = View.GONE
                }
            }
        }

        // Beobachtet die Weinliste und filtert die Weine nach Kategorie
        viewModel.wineList.observe(
            viewLifecycleOwner,
            Observer { list ->
                wineList = list.filter { it.category == category }
                selectionAdapter.submitList(wineList)
            }
        )
    }
}