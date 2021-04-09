package com.example.testwebview

import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.webkit.JavascriptInterface
import android.webkit.WebView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        toolbar.logoDescription = "IMGARENA"
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        webView.settings.javaScriptEnabled = true
        webView.loadUrl("file:///android_asset/javascript.html");
        webView.addJavascriptInterface(WebAppInterface(this, webView), "Android")

        fab.setOnClickListener {
            webView.post() {
                val context = "MessageTopics.CONTEXT_UPDATE"
                val msg = """ {
                        eventId: "262",
                        roundNo: "1",
                        view: "Leaderboard"
                    } """
                webView.loadUrl("javascript:emit( $context, $msg )")
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    class WebAppInterface(
        private val context: Context,
        private val webView: WebView
    ) {

        /** Show a toast from the web page  */
        @JavascriptInterface
        fun contextUpdate(msg: String) {
            Snackbar.make(webView, "Context Update $msg", Snackbar.LENGTH_LONG)
                .setAction("OK", null).show()
        }

        @JavascriptInterface
        fun selectionUpdate(msg: String) {
            Snackbar.make(webView, "Selection Update $msg", Snackbar.LENGTH_LONG)
                .setAction("OK", null).show()
        }


    }
}