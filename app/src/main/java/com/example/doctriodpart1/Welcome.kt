package com.example.doctriodpart1

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.doctriodpart1.IntroFragment.MainActivity

class Welcome : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        Handler().postDelayed({
            finish()
            startActivity(Intent(applicationContext,MainActivity::class.java))
        },1500)
    }
}
