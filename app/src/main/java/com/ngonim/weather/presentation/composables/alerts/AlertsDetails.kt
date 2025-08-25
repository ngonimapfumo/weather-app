import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ngonim.weather.data.model.GetAlertsResponse

@Composable
fun AlertsDetails(data: GetAlertsResponse) {

    val alerts = listOf(data.alerts?.alert)
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = data.location?.region.toString(),
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold
        )
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.weight(1f)
        ) {
            items(alerts) { alert ->
                AlertCard(data.alerts?.alert)

            }
        }
    }
}

@Composable
fun AlertCard(alert: List<GetAlertsResponse.Alerts.Alert?>?) {
    var headline: String? = null
    var severity: String? = null
    var instruction: String? = null
    var valid: String? = null
    var event: String? = null
    var areas: String? = null
    var colors: Color = Color.White
    val red = Color(0xFFDC2626)
    val orange = Color(0xFFF97316)
    val yellow = Color(0xFFFACC15)

    alert?.forEach {
        headline = it?.headline
        severity = it?.severity
        instruction = it?.instruction
        valid = it?.effective
        event = it?.event
        areas = it?.areas
         colors = when (severity) {
            "Severe" -> red
            "Moderate" -> orange
            "Minor" -> yellow
            else -> Color.White
        }
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    event.toString(),
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 18.sp,
                    modifier = Modifier.weight(1f)
                )
                Surface(
                    color = colors,
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = severity.toString(),
                        color = Color.White,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        fontSize = 12.sp
                    )
                }
            }
            Text("Valid: $valid", fontSize = 13.sp, color = Color.Gray)
            Text(headline.toString(), fontSize = 14.sp, color = Color.DarkGray)
            Text("Affected areas: $areas", fontSize = 12.sp, color = Color.Gray)
            Text(instruction.toString(), fontSize = 14.sp)
        }
    }
}

@Preview
@Composable
fun AlertsDetailsPreview() {
    val sampleAlert = GetAlertsResponse.Alerts.Alert(
        areas = "Kings (Brooklyn); Southwest Suffolk; Southeast Suffolk; Southern Queens; Southern Nassau",
        category = "Met",
        certainty = "Likely",
        desc = "Dangerous rip currents. Life-threatening swimming and surfing conditions expected. Stay out of the surf.",
        effective = "2023-08-24T15:49:00-04:00",
        event = "Rip Current Statement",
        expires = "2023-08-25T20:00:00-04:00",
        headline = "Rip Current Statement issued August 24 at 3:49PM EDT until August 25 at 8:00PM EDT by NWS New York City - Upton",
        instruction = "If caught in a rip current, relax and float. Do not swim against the current. Swim parallel to the shore or signal for help.",
        msgtype = "Alert",
        note = "Alert for New York City, NY",
        severity = "Moderate",
        urgency = "Expected"
    )
    val sampleData = GetAlertsResponse(
        alerts = GetAlertsResponse.Alerts(alert = listOf(sampleAlert)),
        location = GetAlertsResponse.Location(
            country = "USA",
            lat = 40.71,
            localtime = "2023-08-25 10:00",
            localtimeEpoch = 1692961200,
            lon = -74.01,
            name = "New York",
            region = "New York",
            tzId = "America/New_York"
        )
    )
    AlertsDetails(data = sampleData)
}

@Preview
@Composable
fun AlertCardPreview() {
    val sampleAlert = GetAlertsResponse.Alerts.Alert(
        areas = "Kings (Brooklyn); Southwest Suffolk; Southeast Suffolk; Southern Queens; Southern Nassau",
        category = "Met",
        certainty = "Likely",
        desc = "Dangerous rip currents. Life-threatening swimming and surfing conditions expected. Stay out of the surf.",
        effective = "2023-08-24T15:49:00-04:00",
        event = "Rip Current Statement",
        expires = "2023-08-25T20:00:00-04:00",
        headline = "Rip Current Statement issued August 24 at 3:49PM EDT until August 25 at 8:00PM EDT by NWS New York City - Upton",
        instruction = "If caught in a rip current, relax and float. Do not swim against the current. Swim parallel to the shore or signal for help.",
        msgtype = "Alert",
        note = "Alert for New York City, NY",
        severity = "Moderate",
        urgency = "Expected"
    )
    AlertCard(alert = listOf(sampleAlert))
}
