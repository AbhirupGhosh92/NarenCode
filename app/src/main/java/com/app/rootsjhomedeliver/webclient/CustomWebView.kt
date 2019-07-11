package com.app.rootsjhomedeliver.webclient

import android.content.Intent
import android.net.Uri
import android.net.http.SslError
import android.os.Build
import android.support.annotation.RequiresApi
import android.util.Log
import android.webkit.*


class CustomWebView(private val snippet :(intent : Intent) -> Unit) : WebViewClient() {




    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {

        if (request?.url.toString().contains("http"))
        {
            var flag = false
            val uri = Uri.parse(request?.url.toString())
            try {
                if (uri?.host?.contains("whatsapp", ignoreCase = true)!!) {

                    var uriString = "wa.me:/${uri.path}?/${uri.query}"

                    val intent = Intent()
                    intent.setPackage("com.whatsapp")
                    intent.putExtra(Intent.EXTRA_TEXT, uriString)
                    flag = true
                    snippet.invoke(intent)
                }
            }
            catch (e : Exception)
            {
                e.printStackTrace()
            }

            return flag
        }
        else
        {
            try{
                val intent = Intent(Intent.ACTION_SEND)
                snippet.invoke(intent)

            }
            catch (e : Exception)
            {
                e.printStackTrace()
            }
            return false
        }

    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onReceivedError(view: WebView?, request: WebResourceRequest?, error: WebResourceError?) {

        Log.e("WebViewError ${error?.errorCode}",error?.description.toString())
        if(error?.errorCode == -10) {

        }
        else
        {
            super.onReceivedError(view, request, error)
        }

    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onReceivedHttpError(
        view: WebView?,
        request: WebResourceRequest?,
        errorResponse: WebResourceResponse?
    ) {
        Log.e("WebViewHttpError",errorResponse?.reasonPhrase)
        super.onReceivedHttpError(view, request, errorResponse)
    }

    override fun onReceivedSslError(view: WebView?, handler: SslErrorHandler?, error: SslError?) {
        Log.e("WebViewSSLError",error.toString())
        handler?.proceed()
    }

}