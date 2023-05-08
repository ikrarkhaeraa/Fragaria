package com.example.fragaria

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.bumptech.glide.Glide
import androidx.appcompat.app.ActionBar
import com.example.fragaria.databinding.ActivityResultBinding

class ResultActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResultBinding

    companion object {
        const val PHOTO = "photo"
//        const val PERCENT = "percent"
        const val NAME = "text"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        supportActionBar?.setCustomView(R.layout.toolbar_layout)

        val photo = intent.getSerializableExtra(PHOTO)
        Log.d("dapetfoto", photo.toString())
        Glide.with(this@ResultActivity)
            .load(photo)
            .into(binding.photoDetectionResult)

        val disease = intent.getSerializableExtra(NAME)
//        val percent = intent.getSerializableExtra(PERCENT)
        binding.textDisease.text = disease.toString()
//        binding.textPercentResult.text = percent.toString()
        val counterMeasureSehat = getString(R.string.counterMeasureSehat)
        val counterMeasureUlat = getString(R.string.counterMeasureUlat)
        val counterMeasureEmbunTepung = getString(R.string.counterMeasureEmbunTepung)
        val counterMeasureMite = getString(R.string.counterMeasureMite)

        if (disease == "Sehat") {
            binding.textCounterMeasures.text = counterMeasureSehat
        } else if (disease == "Ulat") {
            binding.textCounterMeasures.text = counterMeasureUlat
        } else if (disease == "Embun Tepung") {
            binding.textCounterMeasures.text = counterMeasureEmbunTepung
        } else {
            binding.textCounterMeasures.text = counterMeasureMite
        }

        Log.d("cekCounterMeasureText", binding.textCounterMeasures.text.toString())

        chooseButton()
    }

    private fun chooseButton() {
        binding.apply {
            buttonResultToHomePage.setOnClickListener {
                val intentToHomePage = Intent(this@ResultActivity, HomeActivity::class.java)
                startActivity(intentToHomePage)
            }
            buttonResultToArticle.setOnClickListener {
                val intentToSignIn = Intent(this@ResultActivity, ArticleActivity::class.java)
                startActivity(intentToSignIn)
            }
        }
    }

}