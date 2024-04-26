package com.example.maps.screens.tracking

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.maps.R
import com.example.maps.databinding.FragmentTrackingBinding
import com.example.maps.other.ACTION_START_OR_RESUME_SERVICE
import com.example.maps.services.TrackingService
import com.google.android.gms.maps.GoogleMap
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TrackingFragment : Fragment(R.layout.fragment_tracking) {

    private var _binding: FragmentTrackingBinding? = null
    private val binding get() = _binding!!
    private val vm: TrackingVM by viewModels()
    private var map: GoogleMap? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentTrackingBinding.inflate(inflater, container, false)
        binding.mapView.onCreate(savedInstanceState)
        binding.btnToggleRun.setOnClickListener {
            sendCommandToService(ACTION_START_OR_RESUME_SERVICE)
        }

        binding.mapView.getMapAsync { googleMap ->
            map = googleMap
        }

        return (binding.root)
    }

    private fun sendCommandToService(action: String): Intent {
        return Intent(requireContext(), TrackingService::class.java).also {
            it.action = action
            requireContext().startService(it)
        }
    }

    override fun onResume() {
        super.onResume()
        binding.mapView?.onResume()
    }

    override fun onStart() {
        super.onStart()
        binding.mapView?.onStart()
    }

    override fun onStop() {
        super.onStop()
        binding.mapView?.onStop()
    }

    override fun onPause() {
        super.onPause()
        binding.mapView?.onPause()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        binding.mapView?.onLowMemory()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        binding.mapView?.onSaveInstanceState(outState)
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}
