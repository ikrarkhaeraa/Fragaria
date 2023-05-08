package com.example.fragaria

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import com.example.fragaria.databinding.ActivityEmbunTepungBinding
import com.example.fragaria.databinding.ActivityUlatBinding

class EmbunTepungActivity : AppCompatActivity() {

    private lateinit var binding:ActivityEmbunTepungBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityEmbunTepungBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        supportActionBar?.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        supportActionBar?.setCustomView(R.layout.toolbar_layout)
        setContentView(binding.root)
    }
}