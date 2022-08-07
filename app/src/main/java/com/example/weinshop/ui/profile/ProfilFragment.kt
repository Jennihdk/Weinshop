package com.example.weinshop.ui.profile

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.weinshop.R
import com.example.weinshop.databinding.FragmentProfilBinding

class ProfilFragment : Fragment() {

    private lateinit var binding: FragmentProfilBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profil, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val editFirstName = binding.textfirstNameLayout
        val editLastName = binding.textlastNameLayout
        val editAdress = binding.textAdressLayout
        val editPostalCode = binding.textPostalCodeLayout
        val editEmail = binding.textEmailLayout

        val firstName = editFirstName.editText.toString()
        val lastName = editLastName.editText.toString()
        val adress = editAdress.editText.toString()
        val postalCode = editPostalCode.editText.toString()
        val email = editEmail.editText.toString()

        fun saveData() {
            if (!TextUtils.isEmpty(firstName)
                && !TextUtils.isEmpty(lastName)
                && !TextUtils.isEmpty(adress)
                && !TextUtils.isEmpty(postalCode)
                && !TextUtils.isEmpty(email)) {
                Log.e("fsgs", firstName.length.toString())

                Toast.makeText(requireContext(),"Deine Daten wurden gespeichert", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "Deine Daten müssen vollständig sein", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnSafeProfile.setOnClickListener {
            saveData()
        }

    }
}