package com.ngonim.weather.data.model


import com.google.gson.annotations.SerializedName

data class GetAlertsResponse(
    @SerializedName("alerts")
    val alerts: Alerts?,
    @SerializedName("location")
    val location: Location?
) {
    data class Alerts(
        @SerializedName("alert")
        val alert: List<Alert?>?
    ) {
        data class Alert(
            @SerializedName("areas")
            val areas: String?,
            @SerializedName("category")
            val category: String?,
            @SerializedName("certainty")
            val certainty: String?,
            @SerializedName("desc")
            val desc: String?,
            @SerializedName("effective")
            val effective: String?,
            @SerializedName("event")
            val event: String?,
            @SerializedName("expires")
            val expires: String?,
            @SerializedName("headline")
            val headline: String?,
            @SerializedName("instruction")
            val instruction: String?,
            @SerializedName("msgtype")
            val msgtype: String?,
            @SerializedName("note")
            val note: String?,
            @SerializedName("severity")
            val severity: String?,
            @SerializedName("urgency")
            val urgency: String?
        )
    }

    data class Location(
        @SerializedName("country")
        val country: String?,
        @SerializedName("lat")
        val lat: Double?,
        @SerializedName("localtime")
        val localtime: String?,
        @SerializedName("localtime_epoch")
        val localtimeEpoch: Int?,
        @SerializedName("lon")
        val lon: Double?,
        @SerializedName("name")
        val name: String?,
        @SerializedName("region")
        val region: String?,
        @SerializedName("tz_id")
        val tzId: String?
    )
}