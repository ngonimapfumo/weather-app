package com.ngonim.weather.util

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object GenUtil {

    fun formatDate(s:String):String{
        val inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
        val outputFormatter = DateTimeFormatter.ofPattern("dd MMM yyyy, hh:mm a")
        val dateTime = LocalDateTime.parse(s, inputFormatter)
        val formatted = dateTime.format(outputFormatter)
        return formatted
    }

    @SuppressLint("MissingPermission")
    fun getCurrentLocation(context: Context, onLocation: (Location?) -> Unit) {
        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)

        fusedLocationClient.getCurrentLocation(
            Priority.PRIORITY_HIGH_ACCURACY,
            CancellationTokenSource().token
        ).addOnSuccessListener { location ->
            onLocation(location)
        }
    }
}