package com.example.fragaria

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import com.example.fragaria.databinding.ActivityUlatBinding

class UlatActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUlatBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityUlatBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}