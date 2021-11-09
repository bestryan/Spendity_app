package com.example.my_expanse_budget_project

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler

class SplashAct : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        supportActionBar?.hide()

        Handler().postDelayed({
          val intent = Intent(this@SplashAct, MainActivity::class.java)
            startActivity(intent)
            finish()
        },3000)
    }
}