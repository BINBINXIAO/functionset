package com.zxb.functionset.statusbar

import android.os.Bundle
import com.zxb.functionset.R

/**
 * @author:zxb
 * @date:2024/08/09
 */
class StatusBar_Activity : StatusBar_BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.statusbar_activity)
//        initStatusBar(this,R.color.black)
        initStatusBar(this,R.color.white)
    }
}