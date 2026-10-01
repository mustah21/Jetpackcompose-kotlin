package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class Jetpack : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
fun ShowPicture() {
    val image = painterResource(R.drawable.bg_compose_background)
    Column {
        Image(
            painter = image,
            contentDescription = null,
            contentScale = ContentScale.Fit,
        )
        InitialText(
            "Jetpack Compose tutorial",

            "Jetpack Compose is a modern toolkit for building native Android UI." +
                    " Compose simplifies and accelerates UI development on Android with less code," +
                    " powerful tools, and intuitive Kotlin APIs.",

            "In this tutorial, you build a simple UI component with declarative functions. " +
                    "You call Compose functions to say what elements you want " +
                    "and the Compose compiler does the rest. Compose is built around" +
                    " Composable functions. These functions let you define your app\\'s " +
                    "UI programmatically because they let you describe how it should look" +
                    " and provide data dependencies, rather than focus on the process of the UI\\'s" +
                    " construction, such as initializing an element and then attaching it to a parent. " +
                    "To create a Composable function," +
                    " you add the @Composable annotation to the function name.\n"
        )
    }
}

@Composable
fun InitialText(
    message: String,
    message2: String,
    message3: String,
    modifier: Modifier = Modifier
) {
    Column {
        Text(
            text = message,
            fontSize = 24.sp,
            modifier = Modifier
                .padding(16.dp)
        )
        Text(
            text = message2,
            textAlign = TextAlign.Justify,
            modifier = Modifier.padding(start = 16.dp, end = 16.dp)
        )
        Text(
            text = message3,
            textAlign = TextAlign.Justify,
            modifier = Modifier.padding(16.dp)
        )

    }
}
