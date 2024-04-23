package com.example.maps.screens.setup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.maps.R
import com.example.maps.databinding.FragmentSetupBinding

class SetupFragment : Fragment(R.layout.fragment_setup) {

    private var _binding: FragmentSetupBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentSetupBinding.inflate(inflater, container, false)

        bindUI()

        return (binding.root)
    }

    private fun bindUI() {
        binding.buttonContinue.setOnClickListener {
            findNavController().navigate(R.id.to_runFragment)
        }
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}
