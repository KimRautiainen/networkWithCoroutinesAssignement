package com.example.networkwithcoroutinesassignement

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import com.example.networkwithcoroutinesassignement.ui.theme.NetworkWithCoroutinesAssignementTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException
import java.net.URL

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val imgUrl = URL("https://users.metropolia.fi/~jarkkov/folderimage.jpg")
            var imageBitmap by remember { mutableStateOf<Bitmap?>(null) }

            LaunchedEffect(Unit) {
                imageBitmap = getImg(imgUrl)
            }

            NetworkWithCoroutinesAssignementTheme {
                Surface(

                ) {
                    ShowImage(bitmap = imageBitmap)
                }
            }
        }
    }


    private suspend fun getImg(url: URL): Bitmap? {
        return withContext(Dispatchers.IO) {
            try {
                val connection = url.openConnection()
                connection.connect()
                val inputStream = connection.getInputStream()
                BitmapFactory.decodeStream(inputStream)
            } catch (e: IOException) {
                e.printStackTrace()
                null
            }
        }
    }


    @Composable
    fun ShowImage(bitmap: Bitmap?) {
        bitmap?.let {
            Image(
                bitmap = it.asImageBitmap(),
                contentDescription = "Downloaded image",
                modifier = Modifier.fillMaxWidth(),
                contentScale = ContentScale.Fit
            )
        }
    }
}
@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    NetworkWithCoroutinesAssignementTheme {

    }
}