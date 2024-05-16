package com.example.maps.other

import android.content.Context
import android.widget.TextView
import com.example.maps.R
import com.example.maps.model.entity.RunEntity
import com.github.mikephil.charting.components.MarkerView
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.utils.MPPointF
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

private const val METERS_IN_KM = 1000f

class CustomMarkerView(
    val runEntity: List<RunEntity>,
    c: Context,
    layoutId: Int
) : MarkerView(c, layoutId) {

    override fun getOffset(): MPPointF {
        return MPPointF(-width / 2f, -height.toFloat())
    }

    override fun refreshContent(e: Entry?, highlight: Highlight?) {
        super.refreshContent(e, highlight)
        if (e == null) return
        val currentRunId = e.x.toInt()
        val runEntity = runEntity[currentRunId]

        val date = findViewById<TextView>(R.id.tvDate)
        val avgSP = findViewById<TextView>(R.id.tvAvgSpeed)
        val distance = findViewById<TextView>(R.id.tvDistance)
        val dur = findViewById<TextView>(R.id.tvDuration)
        val calories = findViewById<TextView>(R.id.tvCaloriesBurned)


        val calendar = Calendar.getInstance().apply {
            timeInMillis = runEntity.timeInMillis
        }
        val dateFormat = SimpleDateFormat(context.getString(R.string.data_format), Locale.getDefault())
        date.text = dateFormat.format(calendar.time)

        val avgSpeed = "${runEntity.avgSpeedInKMH}${context.getString(R.string.avg_speed_kmh)}"
        avgSP.text = avgSpeed

        val distanceInKm = "${runEntity.distanceInMeters / METERS_IN_KM}${context.getString(R.string.distance_km)}"
        distance.text = distanceInKm

        dur.text = TrackingUtility.getFormattedStopWatchTime(runEntity.timeInMillis)

        val caloriesBurned = "${runEntity.caloriesBurned}${context.getString(R.string.calories_kcal)}"
        calories.text = caloriesBurned
    }
}
