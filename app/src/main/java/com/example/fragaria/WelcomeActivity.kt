package com.example.fragaria

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.fragaria.databinding.ActivityWelcomeBinding

class WelcomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWelcomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        started()
    }

    private fun started() {
        binding.apply {
            buttonWelcome.setOnClickListener {
                val intentToHomePage = Intent(this@WelcomeActivity, HomeActivity::class.java)
                startActivity(intentToHomePage)
            }
        }
    }

}