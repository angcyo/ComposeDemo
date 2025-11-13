package com.angcyo.compose.demo

import com.angcyo.library.component.hawk.HawkPropertyValue

/**
 * @author <a href="mailto:angcyo@126.com">angcyo</a>
 * @date 2025/11/13
 */
object AppKeys {

    /**自动启动*/
    var bootAutoStart: Boolean by HawkPropertyValue<Any, Boolean>(true)

}