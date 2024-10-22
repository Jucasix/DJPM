package ipca.example.dailynews.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import ipca.example.dailynews.R
import ipca.example.dailynews.models.Article

@Composable
fun RowArticle(modifier: Modifier, article: Article) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        // Carrega a imagem de um URL, ou mostra a imagem de fallback se a URL for null
        val imageUrl = article.urlToImage
        if (imageUrl != null) {
            AsyncImage(
                model = imageUrl,
                contentDescription = null,
                modifier = Modifier
                    .size(100.dp),
                contentScale = ContentScale.Crop,
                error = painterResource(id = R.drawable.baseline_insert_photo_24) // Imagem de fallback
            )
        } else {
            Image(
                painter = painterResource(id = R.drawable.baseline_insert_photo_24),
                contentDescription = null,
                modifier = Modifier
                    .size(100.dp),
                contentScale = ContentScale.Crop
            )
        }

        Spacer(modifier = Modifier.width(8.dp))

        // Coloca os textos na outra coluna
        Column {
            Text(text = article.title ?: "Sem Título", fontWeight = FontWeight.Bold, fontSize = 16.sp)
            Text(text = article.description ?: "Sem Descrição", fontSize = 14.sp)
            Text(text = article.publishedAt.toString(), fontSize = 12.sp, color = Color.Gray)
        }
    }
}

