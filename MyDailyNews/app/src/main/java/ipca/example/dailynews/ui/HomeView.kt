package ipca.example.dailynews.ui

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@Composable
fun HomeView(
    modifier: Modifier = Modifier,
    navController: NavController = rememberNavController()
) {
    val viewModel: HomeViewModel = viewModel()
    val uiState by viewModel.uiState.collectAsState()

    Column(modifier = Modifier.fillMaxSize()) {
        // Conteúdo principal
        HomeViewContent(
            modifier = modifier,
            navController = navController,
            uiState = uiState,
            homeViewModel = viewModel // Passa o viewModel corretamente
        )

        // Atualiza os artigos assim que o composable é exibido
        LaunchedEffect(Unit) {
            viewModel.fetchArticles()
        }
    }
}

@Composable
fun HomeViewContent(
    modifier: Modifier = Modifier,
    navController: NavController,
    uiState: ArticlesState,
    homeViewModel: HomeViewModel
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        when {
            uiState.isLoading -> {
                Text("Loading articles...")
            }
            uiState.error != null -> {
                Text("Error: ${uiState.error}")
            }
            uiState.articles.isEmpty() -> {
                Text("No articles found!")
            }
            else -> {
                LazyColumn(modifier = modifier.fillMaxSize()) {
                    itemsIndexed(uiState.articles) { index, article ->
                        RowArticle(
                            modifier = Modifier.padding(8.dp),
                            article = article,
                            navController = navController,
                            homeViewModel = homeViewModel,
                            onClick = {
                                navController.navigate("article_detail/${article.url}")
                            }
                        )
                    }
                }
            }
        }
    }
}
