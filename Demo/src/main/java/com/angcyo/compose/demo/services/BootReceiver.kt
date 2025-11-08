package com.angcyo.compose.demo.services

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent


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
    override fun onReceive(context: Context, intent: Intent) {
        val action = intent.action
        // 启动前台服务或调度任务
        KeepAliveService.notifyText = action
        KeepAliveService.start(context)
        if (Intent.ACTION_BOOT_COMPLETED == action ||
            Intent.ACTION_POWER_CONNECTED == action ||
            Intent.ACTION_PACKAGE_CHANGED == action ||
            Intent.ACTION_INPUT_METHOD_CHANGED == action ||
            Intent.ACTION_DATE_CHANGED == action ||
            Intent.ACTION_BATTERY_CHANGED == action ||
            Intent.ACTION_TIME_CHANGED == action ||
            Intent.ACTION_CONFIGURATION_CHANGED == action
        ) {
            //
        }
    }
}