package com.angcyo.compose.demo.screens

import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.angcyo.compose.app.acc.AccPermission
import com.angcyo.compose.app.acc.BaseAccService
import com.angcyo.compose.basics.NotificationHelper
import com.angcyo.compose.basics.coroutine.sleep
import com.angcyo.compose.basics.requestIgnoreBatteryOptimizations
import com.angcyo.compose.basics.startApp
import com.angcyo.compose.basics.startAppWithRoot
import com.angcyo.compose.basics.toast
import com.angcyo.compose.basics.turnScreenOnOff
import com.angcyo.compose.basics.unit.toTime
import com.angcyo.compose.core.composes.FullscreenLoading
import com.angcyo.compose.core.composes.LastLoadMoreItem
import com.angcyo.compose.core.composes.lastItem
import com.angcyo.compose.core.nav.LocalNavRouter
import com.angcyo.compose.core.objectbox.MessageLogModel
import com.angcyo.compose.core.screen.ScaffoldListScreen
import com.angcyo.compose.core.viewmodel.vmApp
import com.angcyo.compose.demo.services.HeartbeatWorker
import com.angcyo.compose.demo.services.KeepAliveService
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

/**
 * @author <a href="mailto:angcyo@126.com">angcyo</a>
 * @date 2025/11/10
 */

object KeepAliveScreen {
    /**飞书的包名*/
    const val FEI_SHU_PACKAGE = "com.ss.android.lark"

    /**QQ的包名*/
    const val QQ_PACKAGE = "com.tencent.mobileqq"
}

/**保活测试界面*/
@PreviewScreenSizes
@Composable
fun KeepAliveScreen() {
    val messageLogModel = vmApp<MessageLogModel>()
    val router = LocalNavRouter.current
    val activity = LocalActivity.current
    val context = LocalContext.current

    val scope = rememberCoroutineScope()
    var isRefreshing by remember { mutableStateOf(true) }
    var isLoadingMore by remember { mutableStateOf(messageLogModel.page.haveLoadMore) }
    var contentRefresh by remember { mutableIntStateOf(0) }
    var haveAccPermission = BaseAccService.accServiceConnectedData.observeAsState()

    //L.d("log...build")

    LaunchedEffect(Unit) {
        //L.d("log...first")
        scope.launch {
            isRefreshing = true
            messageLogModel.queryRefresh()
            isRefreshing = false
        }
    }

    ScaffoldListScreen(
        isRefreshing = isRefreshing,
        onRefresh = {
            scope.launch {
                isRefreshing = true
                messageLogModel.queryRefresh()
                sleep(300)
                isRefreshing = false
            }
        },
        /*contentRefreshState = contentRefresh*/
    ) {
        //L.d("log...build screen ${contentRefresh}")
        val messageLogList = messageLogModel.messageLogData.value!!
        contentRefresh

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
                            context, KeepAliveService.CHANNEL_ID
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
                if (haveAccPermission.value != true) {
                    Button(onClick = {
                        AccPermission.openAccessibilityActivity(context)
                    }) {
                        Text(text = "打开无障碍服务")
                    }
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
                Button(onClick = {
                    messageLogModel.clearAllLog()
                    contentRefresh++
                }) {
                    Text(text = "清空日志")
                }
                Button(onClick = {
                    scope.launch {
                        sleep(3000)
                        turnScreenOnOff()
                    }
                }) {
                    Text(text = "亮屏")
                }
                Button(onClick = {
                    turnScreenOnOff(false, accService = BaseAccService.lastService)
                }) {
                    Text(text = "灭屏")
                }
            }
        }
        //--
        if (isRefreshing) {
            //--
            item("loading", "loading") {
                FullscreenLoading(height = 300.dp)
            }
            //--
            lastItem()
        } else {
            itemsIndexed(
                messageLogList,
                { index, item -> item.entityId },
                { index, item -> "messageLog" }) { index, item ->
                ListItem(
                    overlineContent = { Text(item.createTime.toTime()) },
                    headlineContent = {
                        Text(
                            item.content ?: "",
                            fontSize = 12.sp,
                            /*modifier = Modifier.bounds(),*/
                            /*autoSize = TextAutoSize.StepBased()*/
                        )
                    },
                )
            }
            //--
            if (messageLogList.isEmpty()) {
                item("loading", "loading") {
                    FullscreenLoading(height = 300.dp) {
                        Text("~暂无数据~")
                    }
                }
            } else {
                lastItem {
                    LastLoadMoreItem(messageLogModel.page.haveLoadMore) {
                        scope.launch {
                            //L.d("log...more 1")
                            messageLogModel.queryLoadMore()
                            //isLoadingMore = messageLogModel.page.haveLoadMore
                            //isRefreshing = false
                            contentRefresh++
                            //L.d("log...more ${messageLogModel.page.haveLoadMore}")
                        }
                    }
                }
            }
        }
    }
}