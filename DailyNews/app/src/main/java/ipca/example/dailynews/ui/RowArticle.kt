package ipca.example.dailynews.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ipca.example.dailynews.models.Article

@Composable
fun RowArticle(article: Article) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        // Coloca a imagem numa das colunas
        Box(
            modifier = Modifier
                .size(100.dp)
                .background(Color.Gray)
        )

        Spacer(modifier = Modifier.width(8.dp))

        // Coloca os textos na outra coluna
        Column {
            Text(text = article.title ?: "Sem Título", fontWeight = FontWeight.Bold, fontSize = 16.sp)
            Text(text = article.description ?: "Sem Descrição", fontSize = 14.sp)
            Text(text = article.publishedAt.toString(), fontSize = 12.sp, color = Color.Gray)
        }
    }
}
