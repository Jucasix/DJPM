package ipca.example.dailynews.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ipca.example.dailynews.models.Article
import ipca.example.dailynews.ui.theme.DailyNewsTheme
import java.util.Date


@Composable
fun HomeView(modifier: Modifier = Modifier) {

    val articles = arrayListOf(
        Article(
            "Título 1",
            "Descrição 1",
            "Imagem 1",
            "https://link1.com",
            Date()
        ),
        Article(
            "Título 2",
            "Descrição 2",
            "Imagem 2",
            "https://link2.com",
            Date()
        )
    )

    LazyColumn(modifier = modifier) {
        itemsIndexed(articles) { _, article ->

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
                    Text(text = article.url ?: "Sem URL", fontSize = 12.sp, color = Color.Blue)
                    Text(text = article.publishedAt.toString(), fontSize = 12.sp, color = Color.Gray)
                }
            }

            HorizontalDivider(color = Color.LightGray)
        }
    }
}


@Preview(showBackground = true)
@Composable
fun HomeViewPreview() {
    DailyNewsTheme {
        HomeView()
    }
}