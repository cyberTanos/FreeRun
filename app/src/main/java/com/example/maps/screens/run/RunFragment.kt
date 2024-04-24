package com.example.maps.screens.run

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.maps.R
import com.example.maps.databinding.FragmentRunBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RunFragment : Fragment(R.layout.fragment_run) {

    private var _binding: FragmentRunBinding? = null
    private val binding get() = _binding!!
    private val vm: RunVM by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentRunBinding.inflate(inflater, container, false)

        bindUI()

        return (binding.root)
    }

    private fun bindUI() {
        binding.fab.setOnClickListener {
            findNavController().navigate(R.id.to_trackingFragment)
        }
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}
