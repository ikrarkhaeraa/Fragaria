package com.example.fragaria

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import com.example.fragaria.databinding.ActivityMiteBinding

class MiteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMiteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMiteBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mite)
        supportActionBar?.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        supportActionBar?.setCustomView(R.layout.toolbar_layout)
    }
}