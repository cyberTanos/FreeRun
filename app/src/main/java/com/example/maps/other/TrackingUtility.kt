package com.example.maps.other

import android.content.Context
import pub.devrel.easypermissions.EasyPermissions

object TrackingUtility {

    fun hasLocationPermission(context: Context): Boolean {
        return EasyPermissions.hasPermissions(
            context,
            android.Manifest.permission.ACCESS_FINE_LOCATION,
            android.Manifest.permission.ACCESS_COARSE_LOCATION,
            android.Manifest.permission.ACCESS_BACKGROUND_LOCATION
        )
    }
}
