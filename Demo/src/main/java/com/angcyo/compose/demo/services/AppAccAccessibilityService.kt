package com.angcyo.compose.demo.services

import android.view.KeyEvent
import android.view.MotionEvent
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED
import com.angcyo.compose.app.acc.AccAccessibilityService
import com.angcyo.compose.basics.global.lastActivity
import com.angcyo.compose.basics.global.lastContext
import com.angcyo.compose.basics.startApp
import com.angcyo.compose.basics.startAppWithRoot
import com.angcyo.compose.basics.turnScreenOnOff
import com.angcyo.compose.basics.unit.L
import com.angcyo.compose.basics.unit.simpleHash
import com.angcyo.compose.basics.wakeUpAndUnlock
import com.angcyo.compose.core.objectbox.MessageLogEntity
import com.angcyo.compose.demo.screens.KeepAliveScreen

/**
 * Email:angcyo@126.com
 * @author angcyo
 * @date 2025/11/11
 */
class AppAccAccessibilityService : AccAccessibilityService() {

    override fun onServiceConnected() {
        super.onServiceConnected()
        MessageLogEntity.save("无障碍服务已连接: ${simpleHash()}")
    }

    override fun onAccessibilityEvent(event: AccessibilityEvent) {
        super.onAccessibilityEvent(event)
        //L.v("${this.simpleHash()} $event")
        if (event.eventType == TYPE_NOTIFICATION_STATE_CHANGED) {
            if (event.packageName == KeepAliveScreen.QQ_PACKAGE) {
                //来自QQ的消息
                val text = event.text
                MessageLogEntity.save("收到QQ消息: $text")
                val message = text.toString().split(":").lastOrNull()?.trim()?.trimEnd(']')
                if (message?.startsWith("#") == true) {
                    val list = message.substring(1).split("@")
                    if (list.size == 1) {
                        //#xxx
                        val cmd = list[0]
                        val cmdCase = cmd.lowercase()
                    } else if (list.size == 2) {
                        //#xxx@xxx
                        val cmd = list[0]
                        val cmdCase = cmd.lowercase()
                        val value = list[1]
                        val valueCase = value.lowercase()
                        if (cmdCase == "start") {
                            if (!startApp(value)) {
                                startAppWithRoot(value)
                            }
                        } else if (cmdCase == "screen") {
                            if (valueCase == "on") {
                                turnScreenOnOff()
                                //wakeUpAndUnlock(lastActivity ?: lastContext)
                            } else {
                                turnScreenOnOff(false, accService = lastService)
                                //wakeUpAndUnlock(lastActivity ?: lastContext, false)
                            }
                        }
                    }
                }
            }
        }
    }

    override fun onKeyEvent(event: KeyEvent?): Boolean {
        L.v("${this.simpleHash()} $event")
        return super.onKeyEvent(event)
    }

    override fun onMotionEvent(event: MotionEvent) {
        super.onMotionEvent(event)
        L.v("${this.simpleHash()} $event")
    }
}