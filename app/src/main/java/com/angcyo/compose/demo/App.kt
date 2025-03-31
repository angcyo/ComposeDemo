package com.angcyo.compose.demo

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview

/**
 * @author <a href="mailto:angcyo@126.com">angcyo</a>
 * @date 2025/03/31
 */
@Composable
@Preview(showBackground = true)
fun App() {
    Log.i("Compose", "[${Thread.currentThread().name}]...app")
    MaterialTheme {
        var showContent by remember { mutableStateOf(false) }
        Log.i("Compose", "[${Thread.currentThread().name}]showContent[${showContent.javaClass}]:${showContent}")
        Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            Button(onClick = { showContent = !showContent }) {
                Text("Click me!")
            }
            Log.d("Compose", "[${Thread.currentThread().name}]...1")
            AnimatedVisibility(showContent) {
                val greeting = remember { "Greeting().greet()" }
                Column(
                    Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Log.d("Compose", "[${Thread.currentThread().name}]...2")
                    Image(painterResource(R.drawable.ic_launcher_background), null)
                    Text("Compose: $greeting")
                }
            }
        }
    }
}