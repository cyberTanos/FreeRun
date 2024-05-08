package com.example.maps.screens.run

import android.Manifest
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.maps.R
import com.example.maps.databinding.FragmentRunBinding
import com.example.maps.other.REQUEST_CODE_LOCATION_PERMISSION
import com.example.maps.other.TrackingUtility
import com.example.maps.other.setTanyaListener
import dagger.hilt.android.AndroidEntryPoint
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions

@AndroidEntryPoint
class RunFragment : Fragment(R.layout.fragment_run), EasyPermissions.PermissionCallbacks {

    private var _binding: FragmentRunBinding? = null
    private val binding get() = _binding!!
    private val vm: RunVM by viewModels()
    private val adapter = RunAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentRunBinding.inflate(inflater, container, false)

        bindUI()
        sortedBy()

        return binding.root
    }

    private fun bindUI() {
        binding.rvRuns.adapter = adapter

        vm.runsSortedBy.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }

        binding.fab.setOnClickListener {
            findNavController().navigate(R.id.to_trackingFragment)
        }
    }

    private fun sortedBy() {
        binding.spFilter.setTanyaListener { position ->
            when (position) {
                0 -> vm.sortByDate()
                1 -> vm.sortByTimeInMillis()
                2 -> vm.sortByDistance()
                3 -> vm.sortByAvgSpeed()
                4 -> vm.sortByCaloriesBurned()
            }
        }
    }

    private fun requestPermission() {
        if (TrackingUtility.hasLocationPermission(requireContext())) return
        EasyPermissions.requestPermissions(
            this,
            "You need to acept location permission to use this app",
            REQUEST_CODE_LOCATION_PERMISSION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_BACKGROUND_LOCATION
        )
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) = Unit

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            AppSettingsDialog.Builder(this).build().show()
        } else {
            requestPermission()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}
