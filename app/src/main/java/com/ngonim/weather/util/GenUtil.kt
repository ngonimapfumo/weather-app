package com.ngonim.weather.util

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
}