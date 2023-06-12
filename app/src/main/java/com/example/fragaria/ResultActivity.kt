package com.example.fragaria

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.bumptech.glide.Glide
import com.example.fragaria.databinding.ActivityResultBinding
import kotlin.math.max

class ResultActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResultBinding

    companion object {
        const val PHOTO = "photo"
        const val NAME = "text"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val photo = intent.getSerializableExtra(PHOTO)
        Log.d("dapetfoto", photo.toString())
        Glide.with(this@ResultActivity)
            .load(photo)
            .into(binding.photoDetectionResult)

        val disease = intent.getSerializableExtra(NAME)
        binding.textDisease.text = disease.toString()
        val counterMeasureSehat = getString(R.string.counterMeasureSehat)
        val counterMeasureUlat = getString(R.string.counterMeasureUlat)
        val counterMeasureEmbunTepung = getString(R.string.counterMeasureEmbunTepung)
        val counterMeasureMite = getString(R.string.counterMeasureMite)

        when (disease) {
            "Sehat" -> {
                binding.textCounterMeasures.text = counterMeasureSehat
            }
            "Ulat" -> {
                binding.textCounterMeasures.text = counterMeasureUlat
            }
            "Embun Tepung" -> {
                binding.textCounterMeasures.text = counterMeasureEmbunTepung
            }
            else -> {
                binding.textCounterMeasures.text = counterMeasureMite
            }
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