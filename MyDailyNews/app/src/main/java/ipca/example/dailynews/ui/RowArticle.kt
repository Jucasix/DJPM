package ipca.example.dailynews.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import ipca.example.dailynews.R
import ipca.example.dailynews.Screen
import ipca.example.dailynews.models.Article
import ipca.examples.dailynews.encodeURL

@Composable
fun RowArticle(
    modifier: Modifier = Modifier,
    article: Article,
    homeViewModel: HomeViewModel, // Aceitar o parâmetro
    navController: NavController, // Aceitar o parâmetro
    onClick: () -> Unit // Adicione este parâmetro para lidar com cliques
){
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable {
                val articleUrl = article.url ?: return@clickable
                if (homeViewModel.uiState.value.articles.isNotEmpty()) {
                    navController.navigate(
                        Screen.ArticleDetail.route.replace(
                            "{articleUrl}",
                            articleUrl.encodeURL()
                        )
                    )
                }
            }
    ) {
        // Exibição da imagem do artigo
        AsyncImage(
            model = article.urlToImage,
            contentDescription = null,
            modifier = Modifier.size(100.dp),
            contentScale = ContentScale.Crop,
            error = painterResource(id = R.drawable.baseline_insert_photo_24)
        )

        Spacer(modifier = Modifier.width(8.dp))

        // Exibição dos detalhes do artigo
        Column {
            Text(
                text = article.title ?: "Sem Título",
                style = MaterialTheme.typography.titleMedium,
                maxLines = 2,
                modifier = Modifier.padding(bottom = 4.dp)
            )
            Text(
                text = article.description ?: "Sem Descrição",
                style = MaterialTheme.typography.bodySmall,
                maxLines = 3,
                color = Color.Gray
            )
            Text(
                text = article.publishedAt.toString(),
                style = MaterialTheme.typography.labelSmall,
                color = Color.Gray,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
    }
}
