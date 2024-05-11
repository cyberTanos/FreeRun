package com.example.maps.screens.setup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.maps.R
import com.example.maps.databinding.FragmentSetupBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SetupFragment : Fragment(R.layout.fragment_setup) {

    private var _binding: FragmentSetupBinding? = null
    private val binding get() = _binding!!

    private val vm: SetupVM by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentSetupBinding.inflate(inflater, container, false)

        checkUserExist()
        bindUI()
        observeVM()

        return binding.root
    }

    private fun bindUI() {

        binding.buttonContinue.setOnClickListener {
            val success = vm.checkSaveUser(
                name = binding.titleNameText.editText?.text.toString(),
                weight = binding.titleWeightText.editText?.text.toString(),
            )
            if (success) findNavController().navigate(R.id.to_runFragment)
        }
    }

    private fun checkUserExist() {
        if (vm.getSavedUser()) {
            findNavController().navigate(R.id.to_runFragment)
        }
    }

    private fun observeVM() {
        vm.error.observe(viewLifecycleOwner) { error ->
            binding.titleNameText.error = error
        }
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}
