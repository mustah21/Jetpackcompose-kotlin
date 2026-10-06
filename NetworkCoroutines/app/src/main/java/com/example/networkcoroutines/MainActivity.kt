package com.example.networkcoroutines

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.tooling.preview.Preview
import com.example.networkcoroutines.ui.theme.NetworkCoroutinesTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.URL

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NetworkCoroutinesTheme {
                ShowImage(URL("https://users.metropolia.fi/~jarkkov/folderimage.jpg"))
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    NetworkCoroutinesTheme {
        ShowImage(URL("https://users.metropolia.fi/~jarkkov/folderimage.jpg"))
    }
}



@Composable
fun ShowImage(urlText: URL) {
    var result by remember {mutableStateOf<Bitmap?>(null)}

    LaunchedEffect(urlText) {
        result = getImg(urlText)
    }
    result?.let {
        Image(bitmap = it.asImageBitmap(), contentDescription = null)
    }
}

private suspend fun getImg(url: URL): Bitmap? =
    withContext (Dispatchers.IO) {
        url.openStream().use { input -> BitmapFactory.decodeStream(input) }
    }
