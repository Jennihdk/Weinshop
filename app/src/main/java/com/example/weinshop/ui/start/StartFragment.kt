package com.example.weinshop.ui.start

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.example.weinshop.MainActivity
import com.example.weinshop.R
import com.example.weinshop.databinding.FragmentStartBinding

class StartFragment : Fragment() {

    private lateinit var binding: FragmentStartBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_start, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnShop.setOnClickListener {
            findNavController().navigate(StartFragmentDirections.actionStartFragmentToCategoryFragment())
        }

    }

    override fun onStart() {
        super.onStart()

        (activity as MainActivity).toggleToolbar(false)
    }

    override fun onStop() {
        super.onStop()

        (activity as MainActivity).toggleToolbar(true)
    }

}