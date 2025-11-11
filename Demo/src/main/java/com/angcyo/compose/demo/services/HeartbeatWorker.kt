package com.angcyo.compose.demo.services

import android.app.job.JobScheduler
import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import com.angcyo.compose.basics.annotation.Api
import com.angcyo.compose.core.objectbox.MessageLogEntity
import java.util.concurrent.TimeUnit

/**
 * @author <a href="mailto:angcyo@126.com">angcyo</a>
 * @date 2025/11/08
 *
 * [JobScheduler] / [WorkManager]（推荐用于周期性任务）
 *
 * 心跳
 */
class HeartbeatWorker(appContext: Context, params: WorkerParameters) :
    CoroutineWorker(appContext, params) {

    companion object {

        /**排队一个心跳任务*/
        @Api
        fun enqueue(
            context: Context,
            repeatInterval: Long = 10,
            repeatIntervalTimeUnit: TimeUnit = TimeUnit.MINUTES,
            existingPeriodicWorkPolicy: ExistingPeriodicWorkPolicy = ExistingPeriodicWorkPolicy.KEEP,
        ) {
            val request = PeriodicWorkRequestBuilder<HeartbeatWorker>(
                repeatInterval,
                repeatIntervalTimeUnit
            ).build()
            WorkManager.getInstance(context)
                .enqueueUniquePeriodicWork(
                    "HeartbeatWorker",
                    existingPeriodicWorkPolicy,
                    request
                )
        }
    }

    override suspend fun doWork(): Result {
        // 做心跳、检查、上报
        // 示例：与服务器短连接、发送心跳
        // 注意不要阻塞太久
        KeepAliveService.notifyText = "HeartbeatWorker"
        MessageLogEntity.save("HeartbeatWorker:doWork")
        return Result.success()
    }
}