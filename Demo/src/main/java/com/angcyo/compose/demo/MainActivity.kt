package com.angcyo.compose.demo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.LocalActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.angcyo.compose.basics.NotificationHelper
import com.angcyo.compose.basics.requestIgnoreBatteryOptimizations
import com.angcyo.compose.basics.toast
import com.angcyo.compose.core.RunNavApp
import com.angcyo.compose.demo.screens.KeepAliveScreen
import com.angcyo.compose.demo.services.HeartbeatWorker
import com.angcyo.compose.demo.services.KeepAliveService
import com.angcyo.compose.demo.ui.theme.ComposeDemoTheme
import java.util.concurrent.TimeUnit

/**
 * @author <a href="mailto:angcyo@126.com">angcyo</a>
 * @date 2025/11/08
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ComposeDemoTheme {
                //ComposeDemoApp()
                RunNavApp {
                    this["/", "KeepAliveScreen"] = { KeepAliveScreen() }
                }
            }
        }
    }
}

/*@PreviewScreenSizes
@Composable
fun ComposeDemoApp() {
    var currentDestination by rememberSaveable { mutableStateOf(AppDestinations.HOME) }

    NavigationSuiteScaffold(
        navigationSuiteItems = {
            AppDestinations.entries.forEach {
                item(
                    icon = {
                        Icon(
                            it.icon, contentDescription = it.label
                        )
                    },
                    label = { Text(it.label) },
                    selected = it == currentDestination,
                    onClick = { currentDestination = it })
            }
        }) {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            Greeting(
                name = "Android", modifier = Modifier.padding(innerPadding)
            )*//*MessageRow()*//*
            //NavKey
            LazyColumn {
                // Add a single item
                item {
                    Text(text = "First item")
                }

                // Add 5 items
                items(5) { index ->
                    Text(text = "Item: $index")
                }

                // Add another single item
                item {
                    Text(text = "Last item")
                }
            }
        }
    }
}*/

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    val activity = LocalActivity.current
    val context = LocalContext.current
    Column {
        Text(
            text = "Hello $name!", modifier = modifier
        )
        Button(onClick = {
            if (!NotificationHelper.isNotificationEnabled(context)) {
                //toast 提示
                context.toast("请打开通知权限")
                NotificationHelper.requestNotificationPermission(activity)
            }
        }) {
            Text(text = "打开通知权限")
        }
        Button(onClick = {
            if (!NotificationHelper.isChannelEnabled(context, KeepAliveService.CHANNEL_ID)) {
                //toast 提示
                context.toast("请打开通知通道")
                NotificationHelper.openNotificationChannelSettings(
                    activity, KeepAliveService.CHANNEL_ID
                )
            }
        }) {
            Text(text = "打开通道通知")
        }
        Button(onClick = {
            KeepAliveService.start(context)
        }) {
            Text(text = "启动保活服务")
        }
        Button(onClick = {
            HeartbeatWorker.enqueue(context, repeatIntervalTimeUnit = TimeUnit.SECONDS)
        }) {
            Text(text = "启动心跳")
        }
        Button(onClick = {
            context.requestIgnoreBatteryOptimizations()
        }) {
            Text(text = "请求忽略电池优化")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ComposeDemoTheme {
        Greeting("Android")
    }
}