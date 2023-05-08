package com.example.fragaria

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import com.example.fragaria.databinding.ActivityUlatBinding
import com.example.fragaria.databinding.ActivityWelcomeBinding

class UlatActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUlatBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityUlatBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        supportActionBar?.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        supportActionBar?.setCustomView(R.layout.toolbar_layout)
    }
}