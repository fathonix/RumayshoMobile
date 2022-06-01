package com.idn.rumayshomobile.ui.view

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.idn.rumayshomobile.R

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    private lateinit var mHandler: Handler
    private val mRunnable: Runnable = Runnable {
        if (!isFinishing) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        mHandler = Handler(Looper.myLooper()!!)

        mHandler.postDelayed(mRunnable, 2000)
    }

    override fun onDestroy() {
        mHandler.removeCallbacks(mRunnable)
        super.onDestroy()
    }
}