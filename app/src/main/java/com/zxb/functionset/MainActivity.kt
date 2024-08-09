package com.zxb.functionset

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.zxb.functionset.statusbar.StatusBar_Activity
import kotlin.reflect.KClass

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<TextView>(R.id.tv_statusbar).setOnClickListener {
            startActivity(StatusBar_Activity::class);
        }
    }

    private inline fun startActivity(kClass: KClass<out Activity>) {
        val intent = Intent(this, kClass.java);
        startActivity(intent);
    }

}