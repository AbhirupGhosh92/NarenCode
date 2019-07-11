package com.app.rootsjhomedeliver.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.DialogFragment
import android.view.KeyEvent
import android.webkit.WebView
import com.app.rootsjhomedeliver.R
import com.app.rootsjhomedeliver.fragments.IntroDialogFragment
import com.app.rootsjhomedeliver.webclient.CustomWebChrome
import com.app.rootsjhomedeliver.webclient.CustomWebView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var webView : WebView
    private lateinit var webViewClient : CustomWebView
    private lateinit var customWebChrome: CustomWebChrome
    private val WHATSAPP = 523


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        webView = findViewById(R.id.main_web_view)
        initialiseWebView()
        webViewClient = CustomWebView {
            startActivityForResult(it, WHATSAPP)
        }
        customWebChrome = CustomWebChrome()
        webView.webViewClient = webViewClient
        webView.webChromeClient = customWebChrome
        webView.loadUrl(resources.getString(R.string.app_url))

        var dialog = IntroDialogFragment()
        dialog.show(supportFragmentManager,"Main")
        GlobalScope.launch(Dispatchers.Main) {
            delay(3000)
            dialog.dismiss()
        }

    }

    private fun initialiseWebView()
    {
        webView.settings.javaScriptEnabled = true

    }

    override fun onBackPressed() {

        if(webView.canGoBack())
            webView.goBack()
        else
            super.onBackPressed()

    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {
            webView.goBack()
            return true
        }
        return super.onKeyDown(keyCode, event)
    }
}
