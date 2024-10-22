package ipca.example.dailynews.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ipca.example.dailynews.models.Article
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SportsViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(ArticlesState())
    val uiState: StateFlow<ArticlesState> = _uiState

    fun fetchSportsArticles() {
        viewModelScope.launch {
            try {
                _uiState.value = _uiState.value.copy(isLoading = true)
                // Faça a chamada para buscar notícias de desporto
                val sportsArticles = repository.getArticlesByCategory("sports")
                _uiState.value = _uiState.value.copy(articles = sportsArticles, isLoading = false)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(error = e.message, isLoading = false)
            }
        }
    }
}

