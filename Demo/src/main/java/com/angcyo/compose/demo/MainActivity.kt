package com.angcyo.compose.demo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.angcyo.compose.core.RunNavApp
import com.angcyo.compose.core.nav.LocalNavRouter
import com.angcyo.compose.core.nav.NavRouter.Companion.INITIAL_PATH
import com.angcyo.compose.core.screen.ScaffoldListScreen
import com.angcyo.compose.demo.screens.KeepAliveScreen
import com.angcyo.compose.demo.ui.theme.ComposeDemoTheme

/**
 * @author <a href="mailto:angcyo@126.com">angcyo</a>
 * @date 2025/11/08
 *
 * - [Icons](https://m3.material.io/styles/icons/overview)
 * - [Icons](https://fonts.google.com/icons)
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ComposeDemoTheme {
                //ComposeDemoApp()
                RunNavApp {
                    this["/", "ComposeDemo"] = { MainScreen() }
                    this["/KeepAliveScreen", "KeepAliveScreen"] = { KeepAliveScreen() }
                }
            }
        }
    }
}

/**主屏幕*/
@Composable
fun MainScreen() {
    val router = LocalNavRouter.current
    val routeList = LocalNavRouter.current?.routeList ?: emptyList()
    ScaffoldListScreen {
        for (item in routeList) {
            if (item.path != INITIAL_PATH) {
                item(item.path) {
                    ListItem(modifier = Modifier.clickable {
                        router?.push(route = item)
                    }, headlineContent = { Text(item.showLabel) }, leadingContent = {
                        val dark = isSystemInDarkTheme()
                        Icon(
                            Icons.Outlined.Favorite,
                            null,
                            tint = if (dark) Color.White else Color.Unspecified
                        )
                    })
                }
            }
        }
    }
}