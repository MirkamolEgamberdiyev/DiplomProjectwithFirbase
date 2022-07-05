package com.tuit.tuit.ui.login

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.WindowManager
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.Toast
import com.tuit.tuit.R
import com.tuit.tuit.ui.student.MainActivity
import com.tuit.tuit.ui.teacher.TeacherActivity
import com.tuit.tuit.utils.SharedPreferences

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN)
        initViews()
    }
    private fun initViews() {
        countDownTimer()
    }
    private fun countDownTimer() {
        object : CountDownTimer(1000, 1000) {
            override fun onTick(millisUntilFinished: Long) {}
            override fun onFinish() {
                val teacher = SharedPreferences.getGetIsTeacher(this@SplashActivity)
                if (SharedPreferences.isLoggedIn(this@SplashActivity)==true){
                    if (teacher==true){
                        Toast.makeText(this@SplashActivity, "dawdwad", Toast.LENGTH_SHORT).show()
                        forward(TeacherActivity::class.java)
                    }else if (teacher==false){
                        forward(MainActivity::class.java)
                    }
                }

            }
        }.start()
    }
    private fun forward(className:Class<*>){
        val intent = Intent(this,className )
        startActivity(intent)
        finish()
    }
}

