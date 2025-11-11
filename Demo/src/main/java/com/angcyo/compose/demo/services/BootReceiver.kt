package com.angcyo.compose.demo.services

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.core.content.ContextCompat
import com.angcyo.compose.core.objectbox.MessageLogEntity


/**
 * @author <a href="mailto:angcyo@126.com">angcyo</a>
 * @date 2025/11/08
 *
 * 自启动广播
 *
 * ```
 * <uses-permission android:name="android.permission.RECEIVE_BOOT_COMPLETED" />
 * ```
 */
class BootReceiver : BroadcastReceiver() {

    companion object {

        val bootActionList = listOf(
            Intent.ACTION_BOOT_COMPLETED,
            Intent.ACTION_POWER_CONNECTED,
            Intent.ACTION_PACKAGE_CHANGED,
            Intent.ACTION_INPUT_METHOD_CHANGED,
            Intent.ACTION_DATE_CHANGED,
            Intent.ACTION_BATTERY_CHANGED,
            Intent.ACTION_TIME_CHANGED,
            Intent.ACTION_CONFIGURATION_CHANGED,
            Intent.ACTION_SCREEN_OFF,
            Intent.ACTION_SCREEN_ON,
            Intent.ACTION_LOCALE_CHANGED,
            Intent.ACTION_PROVIDER_CHANGED,
            Intent.ACTION_BATTERY_LOW,
            Intent.ACTION_BATTERY_OKAY,
            Intent.ACTION_CAMERA_BUTTON,
            Intent.ACTION_MEDIA_BUTTON,
        )

        /**动态注册*/
        fun register(context: Context) {
            ContextCompat.registerReceiver(
                context, BootReceiver(),
                IntentFilter().apply {
                    bootActionList.forEach {
                        addAction(it)
                    }
                },
                ContextCompat.RECEIVER_NOT_EXPORTED
            )
        }
    }

    override fun onReceive(context: Context, intent: Intent) {
        val action = intent.action
        MessageLogEntity.save("收到广播: $action")
        // 启动前台服务或调度任务
        KeepAliveService.notifyText = action
        KeepAliveService.start(context, action)
        //context.startApp(KeepAliveScreen.FEI_SHU_PACKAGE)
        //context.startAppWithRoot(KeepAliveScreen.FEI_SHU_PACKAGE)
    }
}