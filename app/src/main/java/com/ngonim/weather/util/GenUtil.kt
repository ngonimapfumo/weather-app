package com.ngonim.weather.util

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

object GenUtil {

    fun formatDate(s:String):String{
        val inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
        val outputFormatter = DateTimeFormatter.ofPattern("dd MMM yyyy, hh:mm a")
        val dateTime = LocalDateTime.parse(s, inputFormatter)
        val formatted = dateTime.format(outputFormatter)
        return formatted
    }

    fun formatDayAbbreviation(dateString: String): String {
        val inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.getDefault())
        val date = LocalDate.parse(dateString, inputFormatter)

        // Format to show abbreviated day of week (Mon, Tue, Wed, etc.)
        val outputFormatter = DateTimeFormatter.ofPattern("EEE", Locale.getDefault())
        return date.format(outputFormatter)
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