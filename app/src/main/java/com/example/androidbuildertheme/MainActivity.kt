package com.example.androidbuildertheme

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.androidbuildertheme.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sellerId = BuildConfig.SELLER_ID
        val appName = BuildConfig.APP_NAME
        val themeId = BuildConfig.THEME_ID
        val themeVersion = BuildConfig.THEME_VERSION
        val themeColor = BuildConfig.THEME_COLOR
        val callbackUrl = BuildConfig.CALLBACK_URL

        binding.titleText.text = appName
        binding.themeText.text = "Theme: $themeId v$themeVersion"
        binding.infoText.text = "Seller: $sellerId\nCallback: $callbackUrl"
        binding.root.setBackgroundColor(android.graphics.Color.parseColor(themeColor))
        binding.cardView.setCardBackgroundColor(ContextCompat.getColor(this, android.R.color.white))
    }
}
