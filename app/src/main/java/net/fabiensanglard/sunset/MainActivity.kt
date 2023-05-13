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
                println("Battery percentage = ${batteryPercentage.value}")
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

val stages = arrayListOf<Int>(
    R.drawable.screen0,
    R.drawable.screen1,
    R.drawable.screen2,
    R.drawable.screen3,
    R.drawable.screen4,
    R.drawable.screen5,
    R.drawable.screen6,
    R.drawable.screen7,
    R.drawable.screen8,
    R.drawable.screen9,
    R.drawable.screen10,
    R.drawable.screen11,
    R.drawable.screen12,
    R.drawable.screen13,
    R.drawable.screen14,
    R.drawable.screen15,
    R.drawable.screen16,
    R.drawable.screen17,
    R.drawable.screen18,
    R.drawable.screen19,
    R.drawable.screen20,
    R.drawable.screen21,
    R.drawable.screen22,
    R.drawable.screen23,
    R.drawable.screen24,
    R.drawable.screen25,
    R.drawable.screen26,
    R.drawable.screen27,
    R.drawable.screen28,
    R.drawable.screen29,
    R.drawable.screen30,
    R.drawable.screen31,
    R.drawable.screen32,
    R.drawable.screen33,
    R.drawable.screen34,
    R.drawable.screen35,
    R.drawable.screen36,
    R.drawable.screen37,
    R.drawable.screen38,
    R.drawable.screen39,
    R.drawable.screen40,
    R.drawable.screen41,
    R.drawable.screen42,
    R.drawable.screen43,
    R.drawable.screen44,
    R.drawable.screen45,
    R.drawable.screen46,
    R.drawable.screen47,
    R.drawable.screen48,
    R.drawable.screen49,
    R.drawable.screen50,
    R.drawable.screen51,
    R.drawable.screen52,
    R.drawable.screen53,
    R.drawable.screen54,
    R.drawable.screen55,
    R.drawable.screen56,
    R.drawable.screen57,
    R.drawable.screen58,
    R.drawable.screen59,
    R.drawable.screen60,
    R.drawable.screen61,
    R.drawable.screen62,
    R.drawable.screen63,
    R.drawable.screen64,
    R.drawable.screen65,
    R.drawable.screen66,
    R.drawable.screen67,
    R.drawable.screen68,
    R.drawable.screen69,
    R.drawable.screen70,
    R.drawable.screen71,
    R.drawable.screen72,
    R.drawable.screen73,
    R.drawable.screen74,
    R.drawable.screen75,
    R.drawable.screen76,
    R.drawable.screen77,
    R.drawable.screen78,
    R.drawable.screen79,
    R.drawable.screen80,
    R.drawable.screen81,
    R.drawable.screen82,
    R.drawable.screen83,
    R.drawable.screen84,
    R.drawable.screen85,
    R.drawable.screen86,
    R.drawable.screen87,
    R.drawable.screen88,
    R.drawable.screen89,
    R.drawable.screen90,
    R.drawable.screen91,
    R.drawable.screen92,
    R.drawable.screen93,
    R.drawable.screen94,
    R.drawable.screen95,
    R.drawable.screen96,
    R.drawable.screen97,
    R.drawable.screen98,
    R.drawable.screen99,
    R.drawable.screen100,
)
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
//    Image(
//        painter = painterResource(id = R.drawable.stars2),
//        contentScale = ContentScale.FillBounds,
//        contentDescription = stringResource(id = R.string.app_name),
//        alpha = (1 - (batteryPercentage.value ) )   ,
//    )
}