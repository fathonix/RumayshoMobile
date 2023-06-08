package com.idn.rumayshomobile.ui.view

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.util.Log
import androidx.webkit.WebSettingsCompat
import androidx.webkit.WebViewFeature
import com.bumptech.glide.Glide
import com.idn.rumayshomobile.databinding.ActivityDetailBinding
import com.idn.rumayshomobile.models.post.Posts
import java.text.SimpleDateFormat
import java.util.*

class DetailActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_DATA = "data"
    }

    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpWebView()

        val data = intent.getParcelableExtra<Posts>(EXTRA_DATA)!!
        showPost(data)
    }

    private fun showPost(post: Posts) {
        val htmlContent =
            "<!DOCTYPE html><html><head></head><meta name=viewport content=width=device-width  initial-scale=1.0><body>${post.content?.rendered}</body></html>"

        binding.postWebView.loadData(
            htmlContent,
            "text/html; charset=utf-8",
            "UTF-8"
        )

        Log.d("debug", htmlContent)

        if (!post.embedded?.wpFeaturedMedia.isNullOrEmpty()){
            Glide.with(this)
                .load(post.embedded?.wpFeaturedMedia?.get(0)?.sourceUrl)
                .into(binding.detailImg)
        }

        @Suppress("DEPRECATION")
        binding.tvTitle.text = Html.fromHtml(post.title?.rendered)

        @SuppressLint("SimpleDateFormat")
        val date = post.dateGmt?.let {
            val formatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
            formatter.timeZone = TimeZone.getTimeZone("Etc/UTC")
            formatter.parse(it)
        }

        @SuppressLint("SetTextI18n")
        binding.tvInfo.text = "${post.yoastHeadJson?.twitterMisc?.writtenBy}\n$date"
    }

    private fun setUpWebView() {
        when (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
            Configuration.UI_MODE_NIGHT_YES -> {
                if (WebViewFeature.isFeatureSupported(WebViewFeature.FORCE_DARK)) {
                    WebSettingsCompat.setForceDark(
                        binding.postWebView.settings,
                        WebSettingsCompat.FORCE_DARK_ON
                    )
                }
            }
            Configuration.UI_MODE_NIGHT_NO, Configuration.UI_MODE_NIGHT_UNDEFINED -> {
                if (WebViewFeature.isFeatureSupported(WebViewFeature.FORCE_DARK)) {
                    WebSettingsCompat.setForceDark(
                        binding.postWebView.settings,
                        WebSettingsCompat.FORCE_DARK_OFF
                    )
                }
            }
        }

        binding.postWebView.apply {
            this.fitsSystemWindows = true
            this.setInitialScale(1)
            this.settings.apply {
                loadWithOverviewMode = true
                useWideViewPort = true
                @SuppressLint("SetJavaScriptEnabled")
                javaScriptEnabled = true
            }
        }
    }
}