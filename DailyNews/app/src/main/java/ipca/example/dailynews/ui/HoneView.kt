package ipca.example.dailynews.ui

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import ipca.example.dailynews.models.Article
import ipca.example.dailynews.ui.theme.DailyNewsTheme

@Composable
fun HomeView( modifier: Modifier = Modifier) {

    var articles = arrayListOf(
        Article(
            "dsflsdlfk selkfwçlfmkqw",
            "description",
            "urlToImage",
            "url",
            Date()),
        Article(
            "WEFWEFWEFWF ",
            "description",
            "urlToImage",
            "url",
            Date()),

        )

    LazyColumn {
        itemsIndexed(
            items = articles,
        ){
                index, article ->
            Text(text = article.title!!)
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