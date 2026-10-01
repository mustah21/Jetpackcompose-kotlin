package com.example.myapplication

import android.os.Bundle
import android.widget.Switch
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlin.math.pow

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Surface(modifier = Modifier.fillMaxSize()) {
                BinaryLayout()
            }
        }
    }
}

@Composable
fun BinaryLayout() {

    val switches = remember {
        mutableStateListOf(false, false, false, false, false, false, false, false)
    }
    var total = 0
    for (i in switches.indices) {
        if (switches[i]) {
            total += 2.0.pow(i).toInt()

        }
    }

    Column() {
        Row(
            modifier = Modifier.statusBarsPadding(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {

            for (i in switches.indices) {
                Switch(
                    checked = switches[i],
                    onCheckedChange = { switches[i] = it },
                    modifier = Modifier.scale(0.7f)
                )
            }
        }
        Text(
            text = "Value is: $total",
            modifier = Modifier
                .padding(top = 40.dp, bottom = 16.dp)
        )

    }
}

fun binaryHelper(operator: Int): Int {
    val originalBinary = 2.0.pow(operator).toInt()
    return originalBinary
}


@Composable
fun makeSwitch(
    switchOn: Boolean,
    switchOnChanged: (Boolean) -> Unit,
    op: Int,
    modifier: Modifier = Modifier
): Int {
    val binaryValue = binaryHelper(op)

    Row(
        modifier = Modifier.scale(0.7f)

    ) {
        Switch(
            checked = switchOn,
            onCheckedChange = switchOnChanged,
        )
    }
    return binaryValue
}


@Preview(showBackground = true)
@Composable
fun BinaryPreview() {
    BinaryLayout()
}