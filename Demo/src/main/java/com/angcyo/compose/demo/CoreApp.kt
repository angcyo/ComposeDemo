//package com.angcyo.compose.demo
//
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.compositionLocalOf
//import androidx.compose.ui.tooling.preview.PreviewScreenSizes
//import androidx.navigation3.runtime.NavBackStack
//import androidx.navigation3.runtime.NavKey
//import androidx.navigation3.runtime.entryProvider
//import androidx.navigation3.runtime.rememberNavBackStack
//import androidx.navigation3.ui.NavDisplay
//import kotlinx.serialization.Serializable
//
///**
// * Email:angcyo@126.com
// * @author angcyo
// * @date 2025/11/08
// */
//
///**定义一个场景路由
// * - 页面
// * */
//@Serializable
//data class SceneRoute(
//    /**路由路径*/
//    val path: String = "",
//    /**路由名称*/
//    val name: String = "",
//    /**路由的界面内容*/
//    val content: @Composable () -> Unit = {},
//) : NavKey;
//
///**App导航回退栈
// *
// * - [NavBackStack.add] 添加一个导航
// * - [NavBackStack.remove].[NavBackStack.removeLastOrNull] 移除最后一个导航
// * */
//val LocalNavBackStack = compositionLocalOf<NavBackStack<NavKey>?> { null }
//
///**启动一个带有导航的应用
// * - [Dialog]
// * - [Popup]
// * */
//@Composable
//@PreviewScreenSizes
//fun RunNavApp(
//    routers: List<NavKey> = listOf(
//        SceneRoute(
//            "/",
//            content = {
//                Text("Hello Jetpack Compose")
//            },
//        )
//    ),
//) {
//    val backStack = rememberNavBackStack(*routers.toTypedArray())
//    LocalNavBackStack provides backStack/*CompositionLocalProvider(LocalNavBackStack provides backStack) {
//
//    }*/
//
//    NavDisplay(
//        backStack,
//        onBack = {
//            if (backStack.isNotEmpty()) {
//                backStack.removeLastOrNull()
//            }
//        },
//        entryProvider = entryProvider {
//            //NavEntry(Unit) { Text("Unknown route") }
//            /*NavEntry(key) {
//                *//* ContentGreen("Welcome to Nav3") {
//                     Button(onClick = {
//                         backStack.add(Product("123"))
//                     }) {
//                         Text("Click to navigate")
//                     }
//                 }*//*
//            }*/
//        },
//    )
//}