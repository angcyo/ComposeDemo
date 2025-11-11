package com.angcyo.compose.demo.screens

import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.material3.Button
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.compose.ui.unit.dp
import com.angcyo.compose.basics.NotificationHelper
import com.angcyo.compose.basics.requestIgnoreBatteryOptimizations
import com.angcyo.compose.basics.startApp
import com.angcyo.compose.basics.startAppWithRoot
import com.angcyo.compose.basics.toast
import com.angcyo.compose.basics.unit.size
import com.angcyo.compose.core.nav.LocalNavRouter
import com.angcyo.compose.core.objectbox.MessageLogModel
import com.angcyo.compose.core.screen.ScaffoldListScreen
import com.angcyo.compose.core.viewmodel.vmApp
import com.angcyo.compose.demo.services.HeartbeatWorker
import com.angcyo.compose.demo.services.KeepAliveService
import java.util.concurrent.TimeUnit

/**
 * @author <a href="mailto:angcyo@126.com">angcyo</a>
 * @date 2025/11/10
 */

object KeepAliveScreen {
    const val FEI_SHU_PACKAGE = "com.ss.android.lark"
}

/**保活测试界面*/
@PreviewScreenSizes
@Composable
fun KeepAliveScreen() {
    val messageLogModel = vmApp<MessageLogModel>()
    val messageLogState = messageLogModel.messageLogData.observeAsState()
    val router = LocalNavRouter.current
    val activity = LocalActivity.current
    val context = LocalContext.current
    ScaffoldListScreen {
        item("flow", "flow") {
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(2.dp),
                verticalArrangement = Arrangement.spacedBy(2.dp)
            ) {
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
                    if (!NotificationHelper.isChannelEnabled(
                            context,
                            KeepAliveService.CHANNEL_ID
                        )
                    ) {
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
                Button(onClick = {
                    context.startApp(KeepAliveScreen.FEI_SHU_PACKAGE)
                }) {
                    Text(text = "打开飞书")
                }
                Button(onClick = {
                    context.startAppWithRoot(KeepAliveScreen.FEI_SHU_PACKAGE)
                }) {
                    Text(text = "打开飞书(Root)")
                }
                Button(onClick = {
                    router?.push {
                        KeepAliveScreen()
                    }
                }) {
                    Text(text = "testRoute")
                }
            }
        }
        //--
        items(
            messageLogState.value.size(),
            { index ->
                messageLogState.value!![index].entityId
            },
            { index ->
                "messageLog"
            }) { index ->
            val messageLog = messageLogState.value!![index]
            ListItem(
                headlineContent = { Text(messageLog.content ?: "") },
            )
            //Text("KeepAliveScreen $it")
        }
    }
}