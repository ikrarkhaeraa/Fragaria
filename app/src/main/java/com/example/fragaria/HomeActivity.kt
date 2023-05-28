package com.example.fragaria

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import com.example.fragaria.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        chooseFeature()
    }

    private fun chooseFeature() {
        binding.apply {
            buttonScan.setOnClickListener {
                val intentToScan = Intent(this@HomeActivity, ScanActivity::class.java)
                startActivity(intentToScan)
            }
            buttonArticle.setOnClickListener {
                val intentToArticle = Intent(this@HomeActivity,ArticleActivity::class.java)
                startActivity(intentToArticle)
            }
            buttonGuide.setOnClickListener {
                val intentToGuide = Intent(this@HomeActivity,GuideActivity::class.java)
                startActivity(intentToGuide)
            }
        }
    }

}