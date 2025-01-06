package ipca.example.dailynews.ui

import android.content.Intent
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import ipca.example.dailynews.R
import ipca.example.dailynews.models.Article

@Composable
fun ArticleDetail(
    modifier: Modifier = Modifier,
    articleUrl: String?,
    favoritesViewModel: FavoritesViewModel // Aceitar o ViewModel como parâmetro
) {
    val context = LocalContext.current

    if (articleUrl == null) {
        Text("Artigo não encontrado ou inválido.", Modifier.padding(16.dp))
        return
    }

    val favorites by favoritesViewModel.favorites.collectAsState()
    val article = favorites.find { it.url == articleUrl } ?: Article(
        title = "Placeholder Title",
        description = "Placeholder Description",
        url = articleUrl,
        urlToImage = "",
        publishedAt = null // O campo publishedAt agora é compatível com Date?
    )

    val isFavorite = favorites.any { it.url == article.url }

    Column(modifier = Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(onClick = {
                if (isFavorite) {
                    favoritesViewModel.removeFavorite(article)
                } else {
                    favoritesViewModel.addFavorite(article)
                }
            }) {
                Icon(
                    painter = painterResource(
                        if (isFavorite) R.drawable.baseline_favorite_24 else R.drawable.baseline_favorite_border_24
                    ),
                    contentDescription = "Favorite",
                    tint = Color.Red
                )
            }

            IconButton(onClick = {
                val shareIntent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, "Check out this article: ${article.url}")
                    type = "text/plain"
                }
                context.startActivity(Intent.createChooser(shareIntent, "Share via"))
            }) {
                Icon(
                    painter = painterResource(R.drawable.baseline_share_24),
                    contentDescription = "Share"
                )
            }
        }

        AndroidView(factory = { ctx ->
            WebView(ctx).apply {
                settings.javaScriptEnabled = true
                webViewClient = WebViewClient()
                loadUrl(article.url ?: "")
            }
        }, modifier = Modifier.fillMaxSize())
    }
}
