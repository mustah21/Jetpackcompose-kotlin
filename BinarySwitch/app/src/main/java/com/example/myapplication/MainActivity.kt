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
    var c1 by remember { mutableStateOf(false) }
    var c2 by remember { mutableStateOf(false) }
    var c3 by remember { mutableStateOf(false) }
    var c4 by remember { mutableStateOf(false) }
    var c5 by remember { mutableStateOf(false) }
    var c6 by remember { mutableStateOf(false) }
    var c7 by remember { mutableStateOf(false) }
    var c8 by remember { mutableStateOf(false) }

    var total = 0
    Column() {
        Row(
            modifier = Modifier
                .statusBarsPadding(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {

            val v1 = makeSwitch(
                c1,
                switchOnChanged = { c1 = it },
                op = 0,
            )

            val v2 = makeSwitch(
                c2,
                switchOnChanged = { c2 = it },
                op = 1,
            )

            val v3 = makeSwitch(
                c3,
                switchOnChanged = { c3 = it },
                op = 2,
            )

            val v4 = makeSwitch(
                c4,
                switchOnChanged = { c4 = it },
                op = 3,
            )

            val v5 = makeSwitch(
                c5,
                switchOnChanged = { c5 = it },
                op = 4,
            )

            val v6 = makeSwitch(
                c6,
                switchOnChanged = { c6 = it },
                op = 5
            )

            val v7 = makeSwitch(
                c7,
                switchOnChanged = { c7 = it },
                op = 6
            )

            val v8 = makeSwitch(
                c8,
                switchOnChanged = { c8 = it },
                op = 7
            )

            val m1 = if (c1) v1 else 0
            val m2 = if (c2) v2 else 0
            val m3 = if (c3) v3 else 0
            val m4 = if (c4) v4 else 0
            val m5 = if (c5) v5 else 0
            val m6 = if (c6) v6 else 0
            val m7 = if (c7) v7 else 0
            val m8 = if (c8) v8 else 0

            total = m1 + m2 + m3 + m4 + m5 + m6 + m7 + m8

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