package com.example.maps.other

import android.content.Context
import android.location.Location
import com.example.maps.services.Polyline
import java.util.concurrent.TimeUnit
import pub.devrel.easypermissions.EasyPermissions

private const val TIME_FORMATTING_THRESHOLD = 10
private const val MILLISECONDS_DIVISOR_FOR_TENTHS = 10

object TrackingUtility {

    fun hasLocationPermission(context: Context): Boolean {
        return EasyPermissions.hasPermissions(
            context,
            android.Manifest.permission.ACCESS_FINE_LOCATION,
            android.Manifest.permission.ACCESS_COARSE_LOCATION,
            android.Manifest.permission.ACCESS_BACKGROUND_LOCATION
        )
    }

    fun calculatePolylineLength(polyline: Polyline): Float {
        var distanse = 0f
        for (i in 0..polyline.size - 2) {
            val position1 = polyline[i]
            val position2 = polyline[i + 1]

            val result = FloatArray(1)
            Location.distanceBetween(
                position1.latitude,
                position1.longitude,
                position2.latitude,
                position2.longitude,
                result
            )
            distanse += result[0]
        }
        return distanse
    }

    fun getFormattedStopWatchTime(ms: Long, includeMillis: Boolean = false): String {
        var milliseconds = ms
        val hours = TimeUnit.MILLISECONDS.toHours(milliseconds)
        milliseconds -= TimeUnit.HOURS.toMillis(hours)
        val minutes = TimeUnit.MILLISECONDS.toMinutes(milliseconds)
        milliseconds -= TimeUnit.MINUTES.toMillis(minutes)
        val seconds = TimeUnit.MILLISECONDS.toSeconds(milliseconds)
        if (!includeMillis) {
            return "${if (hours < TIME_FORMATTING_THRESHOLD) "0" else ""}$hours:" +
                    "${if (minutes < TIME_FORMATTING_THRESHOLD) "0" else ""}$minutes" +
                    "${if (seconds < TIME_FORMATTING_THRESHOLD) "0" else ""}$seconds"
        }
        milliseconds -= TimeUnit.SECONDS.toMillis(seconds)
        milliseconds /= MILLISECONDS_DIVISOR_FOR_TENTHS
        return "${if (hours < MILLISECONDS_DIVISOR_FOR_TENTHS) "0" else ""}$hours:" +
                "${if (minutes < MILLISECONDS_DIVISOR_FOR_TENTHS) "0" else ""}$minutes:" +
                "${if (seconds < MILLISECONDS_DIVISOR_FOR_TENTHS) "0" else ""}$seconds:" +
                "${if (milliseconds < MILLISECONDS_DIVISOR_FOR_TENTHS) "0" else ""}$milliseconds"
    }
}
