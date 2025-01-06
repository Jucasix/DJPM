package ipca.example.dailynews.ui

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.compose.runtime.getValue
import androidx.compose.runtime.collectAsState


@Composable
fun FavoritesView(
    homeViewModel: HomeViewModel,
    favoritesViewModel: FavoritesViewModel,
    navController: NavController
) {
    val favorites by favoritesViewModel.favorites.collectAsState()
    Log.d("FavoritesView", "Current favorites: $favorites")

    if (favorites.isEmpty()) {
        Text(
            text = "No favorites yet!",
            modifier = Modifier.padding(16.dp)
        )
    } else {
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(favorites) { article ->
                RowArticle(
                    modifier = Modifier.fillMaxWidth(),
                    article = article,
                    homeViewModel = homeViewModel, // Passar o HomeViewModel
                    navController = navController, // Passar o NavController
                    onClick = {
                        navController.navigate("article_detail/${article.url}")
                    }
                )
            }
        }
    }
}
