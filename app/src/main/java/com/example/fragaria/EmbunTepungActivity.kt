package com.example.fragaria

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import com.example.fragaria.databinding.ActivityEmbunTepungBinding

class EmbunTepungActivity : AppCompatActivity() {

    private lateinit var binding:ActivityEmbunTepungBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityEmbunTepungBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}