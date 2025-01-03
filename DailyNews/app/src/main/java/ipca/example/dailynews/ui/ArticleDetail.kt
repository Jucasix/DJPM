package ipca.example.dailynews.ui

import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import ipca.example.dailynews.models.Article

@Composable
fun ArticleDetail(modifier: Modifier = Modifier, articleUrl: String?) {
    if (articleUrl.isNullOrEmpty()) {
        Text(
            text = "Artigo não encontrado ou URL inválida.",
            modifier = modifier.padding(16.dp)
        )
    } else {
        Box(modifier = modifier.fillMaxSize()) {
            AndroidView(
                factory = { context ->
                    WebView(context).apply {
                        settings.javaScriptEnabled = true
                        settings.loadWithOverviewMode = true
                        settings.useWideViewPort = true
                        settings.setSupportZoom(true)
                        webViewClient = WebViewClient()
                    }
                },
                update = { webView ->
                    webView.loadUrl(articleUrl)
                }
            )
        }
    }
}
