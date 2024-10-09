package ipca.example.dailynews.ui

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ipca.example.dailynews.models.Article
import ipca.example.dailynews.ui.theme.DailyNewsTheme
import java.util.Date

@Composable
fun HomeView(modifier: Modifier = Modifier) {

    val articles = arrayListOf(
        Article(
            "Título 1",
            "Descrição 1",
            "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRx60BLi0s29H9Imq8T9A-WyTOjttXBaD5BaQ&s",
            "https://link1.com",
            Date()
        ),
       Article(
            "Título 2",
            "Descrição 2",
            "url",
            "https://link2.com",
            Date()
        )
    )

    LazyColumn(modifier = modifier) {
        itemsIndexed(articles) { _, article ->
            RowArticle(article = article) // Chama a função RowArticle
            HorizontalDivider(thickness = 1.dp, color = Color.LightGray)
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