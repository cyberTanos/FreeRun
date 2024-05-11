package com.example.maps.screens.tracking

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.maps.R
import com.example.maps.databinding.FragmentTrackingBinding
import com.example.maps.model.entity.RunEntity
import com.example.maps.other.ACTION_PAUSE_SERVICE
import com.example.maps.other.ACTION_START_OR_RESUME_SERVICE
import com.example.maps.other.ACTION_STOP_SERVICE
import com.example.maps.other.MAP_ZOOM
import com.example.maps.other.POLYLINE_COLOR
import com.example.maps.other.POLYLINE_WIDTH
import com.example.maps.other.TrackingUtility
import com.example.maps.services.Polyline
import com.example.maps.services.TrackingService
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.PolylineOptions
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import java.util.Calendar
import kotlin.math.round

@AndroidEntryPoint
class TrackingFragment : Fragment(R.layout.fragment_tracking) {

    private var _binding: FragmentTrackingBinding? = null
    private val binding get() = _binding!!
    private val vm: TrackingVM by viewModels()

    private var isTracking = false
    private var pathPoints = mutableListOf<Polyline>()

    private var map: GoogleMap? = null

    private var curTimeInMillis = 0L

    private var weight = 80f

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentTrackingBinding.inflate(inflater, container, false)
        initMap(savedInstanceState)
        subscribeToObservers()
        createMenu()

        binding.tvButtonToggle?.setOnClickListener {
            toggleRun()
        }

        binding.tvButtonFinish?.setOnClickListener {
            zoomToSeeWholeTrack()
        }

        return binding.root
    }

    private fun initMap(savedInstanceState: Bundle?) {
        binding.mapView.onCreate(savedInstanceState)
        binding.mapView.getMapAsync { googleMap ->
            map = googleMap
            addAllPolylines()
        }
    }

    private fun createMenu() {
        binding.toolbar?.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.miCancelTracking -> {
                    showCancelTrackingDialog()
                    true
                }

                else -> false
            }
        }
    }

    private fun subscribeToObservers() {
        TrackingService.isTracking.observe(viewLifecycleOwner) {
            updateTracking(it)
        }
        TrackingService.pathPoints.observe(viewLifecycleOwner) {
            pathPoints = it
            addLatestPolyline()
            moveCameraToUser()
        }

        TrackingService.timeRunInMillis.observe(viewLifecycleOwner) {
            curTimeInMillis = it
            val formattedTime = TrackingUtility.getFormattedStopWatchTime(curTimeInMillis, true)
            binding.tvTimer.text = formattedTime
        }
    }

    private fun toggleRun() {
        if (isTracking) {
            binding.toolbar?.menu?.getItem(0)?.isVisible = true
            sendCommandToService(ACTION_PAUSE_SERVICE)
        } else {
            sendCommandToService(ACTION_START_OR_RESUME_SERVICE)
        }
    }

    private fun showCancelTrackingDialog() {
        val dialog = MaterialAlertDialogBuilder(requireContext(), com.bumptech.glide.R.style.AlertDialog_AppCompat)
            .setTitle("Cancel the Run?")
            .setMessage("Are you sure to cancel the current run and delete all its data?")
            .setIcon(R.drawable.ic_delete)
            .setPositiveButton("Yes") { _, _ ->
                stopRun()
            }
            .setNegativeButton("No") { dialogInterface, _ ->
                dialogInterface.cancel()
            }
            .create()
        dialog.show()
    }

    private fun stopRun() {
        sendCommandToService(ACTION_STOP_SERVICE)
        binding.toolbar?.menu?.getItem(0)?.isVisible = false
        findNavController().navigate(R.id.to_runFragment)
    }

    private fun updateTracking(isTracking: Boolean) {
        this.isTracking = isTracking
        if (!isTracking) {
            binding.tvButtonToggle?.text = "START"
            binding.tvButtonFinish?.visibility = View.VISIBLE
        } else {
            binding.tvButtonToggle?.text = "STOP"
            binding.toolbar?.menu?.getItem(0)?.isVisible = true
            binding.tvButtonFinish?.visibility = View.GONE
        }
    }

    private fun moveCameraToUser() {
        if (pathPoints.isNotEmpty() && pathPoints.last().isNotEmpty()) {
            map?.animateCamera(
                CameraUpdateFactory.newLatLngZoom(
                    pathPoints.last().last(),
                    MAP_ZOOM
                )
            )
        }
    }

    private fun zoomToSeeWholeTrack() {
        val bounds = LatLngBounds.Builder()
        for (polyline in pathPoints) {
            for (pos in polyline) {
                bounds.include(pos)
            }
        }
        try {
            map?.moveCamera(
                CameraUpdateFactory.newLatLngBounds(
                    bounds.build(),
                    binding.mapView.width,
                    binding.mapView.height,
                    (binding.mapView.height * 0.05f).toInt()
                )
            )
            endRunAndSaveToDb()
        } catch (e: Exception) {
            Toast.makeText(requireContext(), "Вы не двигались или проверьте разрешения", Toast.LENGTH_LONG).show()
            stopRun()
        }
    }

    private fun endRunAndSaveToDb() {
        map?.snapshot { bitmap ->
            var distanceInMeters = 0
            for (polyline in pathPoints) {
                distanceInMeters += TrackingUtility.calculatePolylineLength(polyline).toInt()
            }
            val avgSpeed = round((distanceInMeters / 1000f) / (curTimeInMillis / 1000f / 60 / 60)) * 10f
            val dateTimestamp = Calendar.getInstance().timeInMillis
            val caloriesBurned = ((distanceInMeters / 1000f) * weight).toInt()
            val run = RunEntity(
                id = null,
                image = bitmap,
                timestamp = dateTimestamp,
                avgSpeedInKMH = avgSpeed,
                distanceInMeters = distanceInMeters,
                timeInMillis = curTimeInMillis,
                caloriesBurned = caloriesBurned
            )
            vm.insertRun(run)
            Snackbar.make(
                requireActivity().findViewById(R.id.root),
                "Run saved successfully",
                Snackbar.LENGTH_LONG
            ).show()
            stopRun()
        }
    }

    private fun addAllPolylines() {
        for (polyline in pathPoints) {
            val polylineOptions = PolylineOptions()
                .color(POLYLINE_COLOR)
                .width(POLYLINE_WIDTH)
                .addAll(polyline)
            map?.addPolyline(polylineOptions)
        }
    }

    private fun addLatestPolyline() {
        if (pathPoints.isNotEmpty() && pathPoints.last().size > 1) {
            val preLastLatLng = pathPoints.last()[pathPoints.last().size - 2] // предпоследняя точка
            val lastLatLng = pathPoints.last().last() // последняя точка
            val polylineOptions = PolylineOptions()
                .color(POLYLINE_COLOR)
                .width(POLYLINE_WIDTH)
                .add(preLastLatLng)
                .add(lastLatLng)
            map?.addPolyline(polylineOptions)
        }
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
