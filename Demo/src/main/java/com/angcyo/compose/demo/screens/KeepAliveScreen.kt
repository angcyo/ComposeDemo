package com.angcyo.compose.demo.screens

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.angcyo.compose.core.screen.ScaffoldListScreen

/**
 * @author <a href="mailto:angcyo@126.com">angcyo</a>
 * @date 2025/11/10
 */

/**保活界面*/
@Composable
fun KeepAliveScreen() {
    ScaffoldListScreen {
        items(100) {
            ListItem(
                headlineContent = { Text("Three line list item") },
                supportingContent = { Text("Secondary text that\nspans multiple lines") },
                leadingContent = {
                    Icon(Icons.Filled.Favorite, contentDescription = "Localized description")
                },
                trailingContent = { Text("meta") },
            )
            //Text("KeepAliveScreen $it")
        }
        /*item {
            Text("KeepAliveScreen")
        }*/
    }
}