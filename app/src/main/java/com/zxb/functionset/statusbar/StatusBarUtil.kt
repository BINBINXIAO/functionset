package com.zxb.functionset.statusbar

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import com.zxb.functionset.R

/**
 * @author:zxb
 * @date:2024/08/09
 */
class StatusBarUtil {

    companion object {
        private val STATUS_BAR_HEIGHT_RES_NAME = "status_bar_height"

        @JvmStatic
        fun setWindowImmersive(window: Window) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
                window.decorView.systemUiVisibility =
                    (View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE)
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    window.statusBarColor = Color.TRANSPARENT
                }
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            }
        }

        @JvmStatic
        fun isLightOrDark(color: Int): Boolean {
            var flag = false
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                flag = Color.luminance(color) > 0.5
            } else {
                flag = isLightColor(color)
            }
            return flag
        }

        private fun isLightColor(color: Int): Boolean {
            val darkness =
                1 - (0.299 * Color.red(color) + 0.587 * Color.green(color) + 0.114 * Color.blue(
                    color
                )) / 255
            return darkness < 0.5
        }

        @JvmStatic
        fun changeStatusBarTextColor(color: Int, activity: Activity) {
            if (isLightOrDark(color)) {
                setLightStatusBar(activity) //黑色
            } else {
                clearLightStatusBar(activity) //白色
            }
        }

        private fun clearLightStatusBar(activity: Activity) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                var flags = activity.window.decorView.systemUiVisibility // get current flag
                flags = flags and View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv()
                activity.window.decorView.systemUiVisibility = flags
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                activity.window.statusBarColor = activity.resources.getColor(R.color.grey3)
            } else {
                Log.e("StatusBarUtil", "current system api level is" + Build.VERSION.SDK_INT)
            }
        }

        private fun setLightStatusBar(activity: Activity) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                var flags = activity.window.decorView.systemUiVisibility // get current flag
                flags =
                    flags or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR // add LIGHT_STATUS_BAR to flag
                activity.window.decorView.systemUiVisibility = flags
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                activity.window.statusBarColor = activity.resources.getColor(R.color.white)
            } else {
                Log.e("StatusBarUtil", "current system api level is" + Build.VERSION.SDK_INT)
            }
        }

        @JvmStatic
        fun getStatusBarHeight(activity: Activity): Int {
            return getInternalDimensionSize(activity, STATUS_BAR_HEIGHT_RES_NAME)
        }

        private fun getInternalDimensionSize(context: Context?, key: String): Int {
            if (context == null) {
                Log.e("StatusBarUtil", "" + -1)
                return -1
            }
            val resources = context.resources
            var result = -1
            val resourceId = resources.getIdentifier(key, "dimen", "android")
            if (resourceId > 0) {
                result = resources.getDimensionPixelSize(resourceId)
            }
            return result
        }
    }

}