package com.angcyo.compose.demo.services

import android.app.AlarmManager
import android.app.Notification
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import com.angcyo.compose.basics.NotificationHelper
import com.angcyo.compose.demo.MainActivity


/**
 * @author <a href="mailto:angcyo@126.com">angcyo</a>
 * @date 2025/11/08
 *
 * 保活服务
 *
 * ## RuntimeException
 *
 * ```
 * java.lang.RuntimeException: Unable to create service com.angcyo.compose.demo.services.KeepAliveService: java.lang.SecurityException: Permission Denial: startForeground from pid=16397, uid=10279 requires android.permission.FOREGROUND_SERVICE
 *
 * ...
 *
 * <uses-permission android:name="android.permission.FOREGROUND_SERVICE" />
 * <uses-permission android:name="android.permission.FOREGROUND_SERVICE_DATA_SYNC" />
 *
 * ```
 *
 * ## RuntimeException
 *
 * ```
 * java.lang.RuntimeException: Unable to create service com.angcyo.compose.demo.services.KeepAliveService: android.app.MissingForegroundServiceTypeException: Starting FGS without a type  callerApp=ProcessRecord{7a11ffd 16919:com.angcyo.compose.demo/u0a279} targetSDK=36
 *
 * ...
 * <uses-permission android:name="android.permission.FOREGROUND_SERVICE_DATA_SYNC" />
 * <service
 *             android:name=".services.KeepAliveService"
 *             android:foregroundServiceType="dataSync" />
 * ```
 */
class KeepAliveService : Service() {

    companion object {
        const val CHANNEL_ID = "keep_alive_channel"
        const val CHANNEL_NAME = "keep_alive"
        const val NOTIFY_ID = 1001

        /**通知的文本*/
        var notifyText: String? = null

        /**启动*/
        fun start(context: Context) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                context.startForegroundService(Intent(context, KeepAliveService::class.java))
            } else {
                context.startService(Intent(context, KeepAliveService::class.java))
            }
        }
    }

    override fun onCreate() {
        super.onCreate()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationHelper.createNotificationChannel(this, CHANNEL_ID, CHANNEL_NAME)
        }
        // Android O+ use startForegroundService to start; must call startForeground soon after
        startForeground(NOTIFY_ID, showNotification())
    }

    /**
     * - [START_STICKY]
     * - [START_NOT_STICKY]
     * - [START_REDELIVER_INTENT]
     * */
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        // 做必要的心跳/检查/任务
        if (startId > 1) {//第几次启动
            notifyText = "onStartCommand $startId"
            showNotification()
        }
        return START_STICKY // 被系统杀后可能会尝试重启服务
    }

    override fun onRebind(intent: Intent?) {
        super.onRebind(intent)
    }

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onTaskRemoved(rootIntent: Intent?) {
        // 应用从最近任务被划掉时，安排短延时重启
        val restartIntent = Intent(applicationContext, KeepAliveService::class.java)
        val pi = PendingIntent.getService(
            applicationContext,
            0,
            restartIntent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_ONE_SHOT
        )
        val am = getSystemService(ALARM_SERVICE) as AlarmManager
        val triggerAt = System.currentTimeMillis() + 1000
        am[AlarmManager.RTC_WAKEUP, triggerAt] = pi
        super.onTaskRemoved(rootIntent)
    }

    /**显示一个通知*/
    private fun showNotification(): Notification {
        return NotificationHelper.showNotification(
            this,
            CHANNEL_ID,
            NOTIFY_ID,
            "App 正在运行",
            notifyText ?: "保活服务已启动",
            PendingIntent.getActivity(
                this,
                0,
                Intent(
                    this,
                    MainActivity::class.java
                ),
                PendingIntent.FLAG_IMMUTABLE
            )
        )
    }
}