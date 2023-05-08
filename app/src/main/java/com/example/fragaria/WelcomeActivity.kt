package com.example.fragaria

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import com.example.fragaria.databinding.ActivityWelcomeBinding

class WelcomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWelcomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        supportActionBar?.setCustomView(R.layout.toolbar_layout)
        started()
    }

    private fun started() {
        binding.apply {
            buttonWelcome.setOnClickListener {
                val intentToHomePage = Intent(this@WelcomeActivity, HomeActivity::class.java)
                startActivity(intentToHomePage)
//             val toast = Toast.makeText(applicationContext, "toast", Toast.LENGTH_SHORT)
//             toast.show()
            }
        }
    }

}