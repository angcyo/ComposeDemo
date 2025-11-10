package com.angcyo.compose.demo.services

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.core.content.ContextCompat
import com.angcyo.compose.basics.startAppWithRoot
import com.angcyo.compose.demo.screens.KeepAliveScreen


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
        /**动态注册*/
        fun register(context: Context) {
            ContextCompat.registerReceiver(context, BootReceiver(), IntentFilter().apply {
                addAction(Intent.ACTION_BOOT_COMPLETED)
                addAction(Intent.ACTION_POWER_CONNECTED)
                addAction(Intent.ACTION_PACKAGE_CHANGED)
                addAction(Intent.ACTION_INPUT_METHOD_CHANGED)
                addAction(Intent.ACTION_DATE_CHANGED)
                addAction(Intent.ACTION_BATTERY_CHANGED)
                addAction(Intent.ACTION_TIME_CHANGED)
                addAction(Intent.ACTION_CONFIGURATION_CHANGED)
                addAction(Intent.ACTION_SCREEN_OFF)
                addAction(Intent.ACTION_SCREEN_ON)
                addAction(Intent.ACTION_LOCALE_CHANGED)
                addAction(Intent.ACTION_PROVIDER_CHANGED)
                addAction(Intent.ACTION_BATTERY_LOW)
                addAction(Intent.ACTION_BATTERY_OKAY)
                addAction(Intent.ACTION_CAMERA_BUTTON)
                addAction(Intent.ACTION_MEDIA_BUTTON)
            }, ContextCompat.RECEIVER_NOT_EXPORTED)
        }
    }

    override fun onReceive(context: Context, intent: Intent) {
        val action = intent.action
        // 启动前台服务或调度任务
        KeepAliveService.notifyText = action
        KeepAliveService.start(context, action)
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
        //context.startApp(KeepAliveScreen.FEI_SHU_PACKAGE)
        context.startAppWithRoot(KeepAliveScreen.FEI_SHU_PACKAGE)
    }
}