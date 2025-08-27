import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ngonim.weather.util.GenUtil.formatDate


@Composable
fun WeatherAlertDialog(
    onDismiss: () -> Unit,
    location: String,
    temperature: String,
    heatIndex: String,
    pressure: String,
    lastUpdated: String
) {
    AlertDialog(
        onDismissRequest = { onDismiss() },
        confirmButton = {
            TextButton(onClick = onDismiss, modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)) {
                Text("OK")
            }
        },
        title = {
            Text(
                text = "Weather in $location",
                style = MaterialTheme.typography.titleLarge
            )
        },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Temperature in F:")
                    Text("$temperature °F")
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Pressure:")
                    Text("$pressure mbar")
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Heat Index:")
                    Text("$heatIndex °C")
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Last updated:")
                    Text(formatDate(lastUpdated))
                }
            }
        },
        shape = RoundedCornerShape(16.dp)
    )
}

@Preview
@Composable
fun WeatherAlertDialogPreview() {
    WeatherAlertDialog(
        onDismiss = {},
        location = "New York",
        temperature = "25",
        pressure = "1013 mbar",
        heatIndex = "50",
        lastUpdated = "10:30 AM"
    )
}





