import android.graphics.fonts.FontStyle
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsPage(
    modifier: Modifier = Modifier
) {
    var isCelsius by remember { mutableStateOf(true) }
    var isKph by remember { mutableStateOf(true) }
    var darkModeEnabled by remember { mutableStateOf(false) }
    var context = LocalContext.current

    Scaffold(
    ) { padding ->
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {

        // Temperature Unit
        Text(text = "Temperature Unit", style = MaterialTheme.typography.bodyLarge)
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(
                onClick = { isCelsius = true },
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isCelsius) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface
                )
            ) {
                Text("°C")
            }
            Button(
                onClick = { isCelsius = false },
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (!isCelsius) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface
                )
            ) {
                Text("°F")
            }
        }

        // Wind Speed Unit
        Text(text = "Wind Speed Unit", style = MaterialTheme.typography.bodyLarge)
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(
                onClick = { isKph = true },
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isKph) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface
                )
            ) {
                Text("kph")
            }
            Button(
                onClick = { isKph = false },
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (!isKph) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface
                )
            ) {
                Text("mph")
            }
        }
        // Dark Mode
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Dark Mode", style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Light)
            Switch(colors = SwitchDefaults.colors(
                checkedThumbColor = Color(0xFF72BE75),
                uncheckedThumbColor = Color(0xFF375E39),
                uncheckedTrackColor = Color.White,
                checkedTrackColor = Color(0xFFF1EEED),
                checkedBorderColor = Color.DarkGray,
                uncheckedBorderColor = Color.DarkGray
            ),
                checked = darkModeEnabled,
                onCheckedChange = {
                    Toast.makeText(context, "Dark Mode Enabled", Toast.LENGTH_SHORT).show()
                    darkModeEnabled = it })
        }
    }
}


    }

@Preview
@Composable
fun SettingsScreenPreview() {
    SettingsPage()
}

