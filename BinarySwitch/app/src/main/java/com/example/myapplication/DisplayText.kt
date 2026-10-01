package com.example.myapplication

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
fun DisplayText(modifier: Modifier = Modifier) {

    Column(modifier = Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            MyText(
                "Text composable\n",
                "Displays text and follows the recommended" +
                        " Material Design guidelines.\n",
                modifier = Modifier
                    .weight(1f)
                    .background(Color(0xFFD0BCFF))
            )

            MyText(
                "Image composable\n",
                "Creates a composable that lays out" +
                        " and draws a given Painter class object.\n",
                modifier = Modifier
                    .weight(1f)
                    .background(
                        Color(0xFFB69DF8)
                    )

            )
        }
        Row(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            MyText(
                "Row composable\n",
                "A layout composable that places its children " +
                        "in a horizontal sequence.\n",
                modifier = Modifier
                    .weight(1f)
                    .background(Color(0xFFF6EDFF))
            )

            MyText(
                "Column composable\n",
                "A layout composable that places its children in a " +
                        "vertical sequence.\n",
                modifier = Modifier
                    .weight(1f)
                    .background(
                        Color(0xFFEADDFF)
                    )

            )

        }
    }
}


@Composable
fun MyText(message: String, message2: String, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxHeight(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,

        ) {
        Text(
            text = message,
            fontSize = 14.sp,
            modifier = Modifier
                .padding(end = 16.dp),
            textAlign = TextAlign.Center,
        )
        Text(
            text = message2,
            fontSize = 24.sp,
            modifier = Modifier
                .padding(16.dp),
            textAlign = TextAlign.Center,

            )
    }

}