package com.angcyo.compose.demo

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layout
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.offset

/**
 * @author <a href="mailto:angcyo@126.com">angcyo</a>
 * @date 2025/03/31
 */
@Composable
@Preview(showBackground = true)
fun App() {
    Log.i("Compose", "[${Thread.currentThread().name}]...app")
    MaterialTheme {
        Log.d("Compose", "[${Thread.currentThread().name}]...MaterialTheme")
        var showContent by remember { mutableStateOf(false) }
        //var showContent by rememberSaveable { mutableStateOf(false) }
        Log.i(
            "Compose",
            "[${Thread.currentThread().name}]showContent[${showContent.javaClass}]:${showContent}"
        )
        Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            Log.d("Compose", "[${Thread.currentThread().name}]...Column")
            Button(onClick = { showContent = !showContent }) {
                Log.d("Compose", "[${Thread.currentThread().name}]...Button")
                Text("Click me!")
            }
            AnimatedVisibility(showContent) {
                Log.d("Compose", "[${Thread.currentThread().name}]...AnimatedVisibility")
                val greeting = remember { "Greeting().greet()" }
                Column(
                    Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Log.d("Compose", "[${Thread.currentThread().name}]...AnimatedVisibility:Column")
                    Image(painterResource(R.drawable.ic_launcher_background), null)
                    Text("Compose: $greeting")
                }
            }
            //--
            var clicks by remember { mutableIntStateOf(0) }
            ClickCounter(clicks) {
                clicks++
            }
            //--
            Box(
                modifier = Modifier
                    .layout { measurable, constraints ->
                        val padding = 10
                        val placeable = measurable.measure(constraints.offset(vertical = -padding))
                        val placeable2 =
                            measurable.measure(constraints.offset(vertical = -padding * 2))
                        layout(placeable.width, placeable2.height + padding) {
                            placeable2.placeRelative(0, padding)
                        }
                    }
                    .clickable {
                        Log.d("Compose", "[${Thread.currentThread().name}]...clickable")
                    }) {
                //LocalContext.current
                Text("Layout Box")
            }
        }
    }
}

/**
 * 重组
 * https://developer.android.google.cn/develop/ui/compose/mental-model?hl=zh-cn#recomposition*/
@Composable
fun ClickCounter(clicks: Int, onClick: () -> Unit) {
    Button(onClick = onClick) {
        Text("I've been clicked $clicks times")
    }
}