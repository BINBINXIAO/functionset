package com.zxb.functionset.statusbar

import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import com.zxb.functionset.R

/**
 * @author:张小兵
 * @e-mail:460116602@qq.com
 * @date:2024/08/09
 */
open class StatusBar_BaseActivity : AppCompatActivity() {

    private var customActivityColor: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)

    }

    fun initStatusBar(activity: AppCompatActivity, backgroundColor: Int) {
        setWindowImmersive(backgroundColor);
        val root =
            (activity.findViewById<View>(android.R.id.content) as ViewGroup).getChildAt(0) as ViewGroup
        var layoutView: View? = null
        var isContainLinearLayout = false
        if (root is LinearLayout) {
            layoutView = root
            isContainLinearLayout = true
        } else {
            val childCount = root.childCount
            for (i in 0 until childCount) {
                layoutView = root.getChildAt(i)
                if (layoutView is LinearLayout) {
                    isContainLinearLayout = true
                    break
                }
            }
        }

        if (isContainLinearLayout && (layoutView as LinearLayout).orientation == LinearLayout.VERTICAL) {
            if ((layoutView as LinearLayout).findViewById<View?>(R.id.header_statu_bar) != null) {
                val statu_bar = layoutView.findViewById<View>(R.id.header_statu_bar)
                statu_bar.setBackgroundColor(resources.getColor(backgroundColor))
                return
            }
            val status_bar = View(activity)
            status_bar.setBackgroundColor(resources.getColor(backgroundColor))
            status_bar.id = R.id.header_statu_bar
            val lp = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            lp.height = StatusBarUtil.getStatusBarHeight(this)
            status_bar.layoutParams = lp
            if (Build.VERSION.SDK_INT >= 19) {
                layoutView.addView(status_bar, 0)
            }
        }

    }

    private fun setWindowImmersive(backgroundColor: Int) {
        StatusBarUtil.setWindowImmersive(window)
        setNavigationBar(
            StatusBarUtil.isLightOrDark(
                ResourcesCompat.getColor(
                    getResources(),
                    backgroundColor,
                    null
                )
            )
        )
        StatusBarUtil.changeStatusBarTextColor(
            ResourcesCompat.getColor(
                getResources(),
                backgroundColor,
                null
            ), this
        )
    }

    fun setNavigationBar(islight: Boolean) {
        var systemUiVisibility = window.decorView.systemUiVisibility
        var bgcolor = R.color.black
        if (islight) {
            bgcolor = R.color.white
            systemUiVisibility = systemUiVisibility or View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
        } else {
            systemUiVisibility =
                systemUiVisibility and View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR.inv()
        }
        window.navigationBarColor = ResourcesCompat.getColor(resources, bgcolor, null)
        window.decorView.systemUiVisibility = systemUiVisibility
    }

    fun getColor(): Int {
        val color: Int
        if (customActivityColor != 0) {
            return customActivityColor
        }
        color = resources.getColor(R.color.primary_navigation_bar_background)
        return color
    }
}