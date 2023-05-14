package net.fabiensanglard.sunset

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf


import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import net.fabiensanglard.sunset.Sunsets.stages

import net.fabiensanglard.sunset.ui.theme.SunsetTheme


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Monitor batteries
        val batteryPercentage = mutableStateOf(0.0f)
        val batteryLevelFilter = IntentFilter(Intent.ACTION_BATTERY_CHANGED)
        val batteryLevelReceiver: BroadcastReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                val level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1)
                val scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1)
                batteryPercentage.value = level / scale.toFloat()
            }
        }

        registerReceiver(batteryLevelReceiver, batteryLevelFilter)

        setContent {
            SunsetTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    Draw(batteryPercentage)
                }
            }
        }
    }
}

fun getDrawableSunset(f: Float) : Int {
    var index = (f * stages.size.toFloat()).toInt()
    if (index >= stages.size) index = stages.size -1
    // Match f (0..1) -> 0..stages.size
    return stages[index]
}

@Composable
fun Draw(batteryPercentage: MutableState<Float>) {
    val id = getDrawableSunset(batteryPercentage.value)
    Image(
        painter = painterResource(id = id),
        contentScale = ContentScale.FillBounds,
        contentDescription = stringResource(id = R.string.app_name)
    )
}