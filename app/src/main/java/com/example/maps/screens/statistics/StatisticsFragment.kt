package com.example.maps.screens.statistics

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.maps.R
import com.example.maps.databinding.FragmentStatisticsBinding
import com.example.maps.other.CustomMarkerView
import com.example.maps.other.TrackingUtility
import com.github.mikephil.charting.components.XAxis.XAxisPosition.BOTTOM
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.round

private const val METERS_IN_KM = 1000f
private const val ROUNDING_NUMBER = 10f

@AndroidEntryPoint
class StatisticsFragment : Fragment(R.layout.fragment_statistics) {

    private var _binding: FragmentStatisticsBinding? = null
    private val binding get() = _binding!!
    private val vm: StatisticsVM by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentStatisticsBinding.inflate(inflater, container, false)

        observeVM()
        setToolbar()
        setupBarChart()

        return binding.root
    }

    private fun observeVM() {
        vm.statisticsLD.observe(viewLifecycleOwner) {
            it?.let {
                val allAvgSpeeds = it.indices.map { i -> BarEntry(i.toFloat(), it[i].avgSpeedInKMH) }
                val barDataSet = BarDataSet(allAvgSpeeds, getString(R.string.bar_chart_descrip_text)).apply {
                    valueTextColor = Color.WHITE
                    color = ContextCompat.getColor(requireContext(), pub.devrel.easypermissions.R.color.material_blue_grey_800)
                }
                binding.barChart.data = BarData(barDataSet)
                binding.barChart.marker = CustomMarkerView(it.reversed(), requireContext(), R.layout.marker_view)
                binding.barChart.invalidate()
            }
        }
        vm.timeRunLD.observe(viewLifecycleOwner) {
            it?.let {
                binding.tvTotalTime.text = TrackingUtility.getFormattedStopWatchTime(it)
            }
        }
        vm.distanceLD.observe(viewLifecycleOwner) {
            it?.let {
                // ADD CONST VAL
                val km = it / METERS_IN_KM
                val totalDistance = round(km * ROUNDING_NUMBER) / ROUNDING_NUMBER
                val totalDistanceString = "$totalDistance ${getString(R.string.distance_km)}"
                binding.tvTotalDistance.text = totalDistanceString
            }
        }
        vm.caloriesBurnedLD.observe(viewLifecycleOwner) {
            it?.let {
                val totalCalories = "$it ${getString(R.string.calories_kcal)}"
                binding.tvTotalCalories.text = totalCalories
            }
        }
        vm.avgSpeedD.observe(viewLifecycleOwner) {
            it?.let {
                val avgSpeed = round(it * ROUNDING_NUMBER) / ROUNDING_NUMBER
                val avgSpeedString = "$avgSpeed ${getString(R.string.avg_speed_kmh)}"
                binding.tvAverageSpeed.text = avgSpeedString
            }
        }
    }

    private fun setToolbar() {
        binding.tvTitle?.text = getString(R.string.change_toolbar_text, vm.getName().uppercase())
    }

    private fun setupBarChart() {
        binding.barChart.xAxis.apply {
            position = BOTTOM
            setDrawLabels(false)
            axisLineColor = Color.BLACK
            textColor = Color.BLACK
            setDrawGridLines(false)
        }
        binding.barChart.axisLeft.apply {
            axisLineColor = Color.BLACK
            textColor = Color.BLACK
            setDrawGridLines(false)
        }
        binding.barChart.axisRight.apply {
            axisLineColor = Color.BLACK
            textColor = Color.BLACK
            setDrawGridLines(false)
        }
        binding.barChart.apply {
            description.text = getString(R.string.bar_chart_descrip_text)
            legend.isEnabled = false
        }
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}
