package com.angcyo.compose.demo.services

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.core.content.ContextCompat
import com.angcyo.compose.basics.hawk.hawkHave
import com.angcyo.compose.basics.hawk.hawkPut
import com.angcyo.compose.basics.startAppWithRoot
import com.angcyo.compose.core.objectbox.MessageLogEntity
import com.angcyo.compose.demo.AppKeys
import com.angcyo.compose.demo.screens.KeepAliveScreen
import java.util.Calendar


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
        if (AppKeys.bootAutoStart) {
            autoStart(context)
        }
    }

    fun autoStart(context: Context) {
        //2025-11-13 08:44:52
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR) //2025
        val month = calendar.get(Calendar.MONTH) + 1 // 11
        val day = calendar.get(Calendar.DAY_OF_MONTH) // 13
        val hours = calendar.get(Calendar.HOUR_OF_DAY) // 8
        val minute = calendar.get(Calendar.MINUTE) // 44
        val seconds = calendar.get(Calendar.SECOND) // 52
        /*if (calendar.firstDayOfWeek != Calendar.MONDAY) {
        }*/
        val week = calendar.get(Calendar.DAY_OF_WEEK).run {
            when (this) {
                Calendar.SUNDAY -> 7
                Calendar.MONDAY -> 1
                Calendar.TUESDAY -> 2
                Calendar.WEDNESDAY -> 3
                Calendar.THURSDAY -> 4
                Calendar.FRIDAY -> 5
                Calendar.SATURDAY -> 6
                else -> 0
            }
        }
        val key = "${year}_${month}_${day}"
        if (key.hawkHave()) {
            return
        }
        if (hours >= 8 && hours <= 9 && minute <= 30 && week != 7 && week != 0) {
            if (context.startAppWithRoot(KeepAliveScreen.FEI_SHU_PACKAGE)) {
                key.hawkPut(true)
            }
        }
    }
}