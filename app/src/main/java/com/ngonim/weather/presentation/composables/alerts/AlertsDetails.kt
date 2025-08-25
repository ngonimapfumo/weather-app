import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.ngonim.weather.data.model.GetAlertsResponse

@Composable
fun AlertsDetails(data: GetAlertsResponse) {

    val alerts = listOf(
        /*Alert(
            icon = "🌊",
            title = ss.toString(),
            severity = severity.toString(),
            severityColor = Color(0xFFF97316), // Orange
            valid = "Aug 24, 15:49 – Aug 25, 20:00",
            description = "Dangerous rip currents. Life-threatening swimming and surfing conditions expected. Stay out of the surf.",
            areas = "Kings (Brooklyn); Southwest Suffolk; Southeast Suffolk; Southern Queens; Southern Nassau",
            instruction = "If caught in a rip current, relax and float. Do not swim against the current. Swim parallel to the shore or signal for help."
        ),
        Alert(
            icon = "🌊",
            title = "Rip Current Statement",
            severity = severity.toString(),
            severityColor = Color(0xFFF97316),
            valid = "Aug 25, 02:15 – Aug 25, 18:00",
            description = "Life-threatening rip currents likely for all people entering the surf zone. Localized beach erosion possible.",
            areas = "Kings (Brooklyn); Southwest Suffolk; Southeast Suffolk; Southern Queens; Southern Nassau",
            instruction = "Always swim near a lifeguard. If caught in a rip current, remain calm, float, and signal for help."
        )*/

        data.alerts?.alert
    )

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

        /*Button(
            onClick = { },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE5E7EB)),
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text("View Past Alerts", color = Color.DarkGray)
        }*/
    }
}

@Composable
fun AlertCard(alert: List<GetAlertsResponse.Alerts.Alert?>?) {
    var headline: String?= null
    var severity: String?= null
    var instruction: String?= null
    var valid: String?= null
    var event: String?= null
    var areas: String?= null
    alert?.forEach {
        headline =  it?.headline
        severity = it?.severity
        instruction = it?.instruction
        valid = it?.effective
        event = it?.event
        areas = it?.areas
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(event.toString(), fontWeight = FontWeight.SemiBold, fontSize = 18.sp, modifier = Modifier.weight(1f))
                Surface(
                    color = Color(0xFFF97316),
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
