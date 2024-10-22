package ipca.example.dailynews.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController

@Composable
fun SportsView(navController: NavController) {
    val viewModel: SportsViewModel = viewModel()
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.fetchSportsArticles()
    }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = LineHeightStyle.Alignment.Center) {
        if (uiState.isLoading) {
            Text("Carregando notícias de desporto...")
        } else if (uiState.error != null) {
            Text("Erro: ${uiState.error}")
        } else if (uiState.articles.isEmpty()) {
            Text("Nenhuma notícia de desporto encontrada!")
        } else {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                itemsIndexed(uiState.articles) { _, article ->
                    RowArticle(article = article)
                }
            }
        }
    }
}
