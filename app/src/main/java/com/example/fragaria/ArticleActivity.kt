package com.example.fragaria

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import com.example.fragaria.databinding.ActivityArticleBinding

class ArticleActivity : AppCompatActivity() {

    private lateinit var binding: ActivityArticleBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityArticleBinding.inflate(layoutInflater)
        setContentView(binding.root)
        chooseArticle()
    }
    private fun chooseArticle() {
        binding.apply {
            cardViewEmbunTepung.setOnClickListener {
                val intentToDetail = Intent(this@ArticleActivity, EmbunTepungActivity::class.java)
                startActivity(intentToDetail)
            }
            cardViewMite.setOnClickListener {
                val intentToDetail = Intent(this@ArticleActivity, MiteActivity::class.java)
                startActivity(intentToDetail)
            }
            cardViewUlat.setOnClickListener {
                val intentToDetail = Intent(this@ArticleActivity, UlatActivity::class.java)
                startActivity(intentToDetail)
            }
        }
    }
}