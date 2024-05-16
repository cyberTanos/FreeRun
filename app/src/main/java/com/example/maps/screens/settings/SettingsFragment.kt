package com.example.maps.screens.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.maps.R
import com.example.maps.databinding.FragmentSettingsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingsFragment : Fragment(R.layout.fragment_settings) {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    private val vm: SettingsVM by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)

        setToolbar()
        bindUI()
        observeVM()

        return binding.root
    }

    private fun bindUI() = with(binding) {
        etName.setText(vm.getName())
        etWeight.setText(vm.getWeight())
        btnApplyChanges.setOnClickListener {
            applyChanges()
        }
    }

    private fun observeVM() {
        vm.error.observe(viewLifecycleOwner) { error ->
            binding.titleNameText.error = error
        }
    }

    private fun applyChanges() {
        val success = vm.applyChanges(
            name = binding.etName.text.toString(),
            weight = binding.etWeight.text.toString()
        )
        if (success) setToolbar()
    }

    private fun setToolbar() {
        binding.tvTitle.text = getString(R.string.change_toolbar_text, vm.getName().uppercase())
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}
