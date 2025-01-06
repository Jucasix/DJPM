package ipca.example.dailynews.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ipca.example.dailynews.models.Article
import ipca.example.dailynews.models.FavoriteArticle
import ipca.example.dailynews.database.FavoriteArticleDao
import ipca.examples.dailynews.parseDate
import ipca.examples.dailynews.toYYYYMMDD
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FavoritesViewModel(private val dao: FavoriteArticleDao) : ViewModel() {

    private val _favorites = MutableStateFlow<List<Article>>(emptyList())
    val favorites: StateFlow<List<Article>> = _favorites.asStateFlow()

    init {
        loadFavorites()
    }

    private fun loadFavorites() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) { // Usa uma thread separada para acessar o banco de dados
                val favoriteArticles = dao.getAllFavorites()
                _favorites.value = favoriteArticles.mapNotNull { favoriteArticle ->
                    Article(
                        title = favoriteArticle.title ?: "Unknown Title",
                        description = favoriteArticle.description ?: "No Description",
                        url = favoriteArticle.url ?: return@mapNotNull null,
                        urlToImage = favoriteArticle.imageUrl ?: "",
                        publishedAt = favoriteArticle.publishedAt?.parseDate()
                    )
                }
            }
        }
    }

    fun addFavorite(article: Article) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val favorite = FavoriteArticle(
                    title = article.title ?: "Unknown Title",
                    description = article.description ?: "No Description",
                    url = article.url ?: "",
                    imageUrl = article.urlToImage ?: "",
                    publishedAt = article.publishedAt?.toYYYYMMDD()
                )
                dao.insertFavorite(favorite)
                loadFavorites() // Recarrega os favoritos
            }
        }
    }

    fun removeFavorite(article: Article) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val favorite = FavoriteArticle(
                    title = article.title ?: "Unknown Title",
                    description = article.description ?: "No Description",
                    url = article.url ?: "",
                    imageUrl = article.urlToImage ?: "",
                    publishedAt = article.publishedAt?.toYYYYMMDD()
                )
                dao.deleteFavorite(favorite)
                loadFavorites() // Recarrega os favoritos
            }
        }
    }

    fun isFavorite(url: String?): Boolean {
        return _favorites.value.any { it.url == url }
    }
}
