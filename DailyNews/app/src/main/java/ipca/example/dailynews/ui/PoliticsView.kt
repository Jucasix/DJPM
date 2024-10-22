package ipca.example.dailynews.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController

@Composable
fun PoliticsView(navController: NavController) {
    val viewModel: PoliticsViewModel = viewModel()
    val uiState by viewModel.uiState.collectAsState()

    HomeViewContent( // Reutilizar a função
        modifier = Modifier.fillMaxSize(),
        navController = navController,
        uiState = uiState
    )

    LaunchedEffect(Unit) {
        viewModel.fetchPoliticsArticles() // Busca notícias de política
    }
}


