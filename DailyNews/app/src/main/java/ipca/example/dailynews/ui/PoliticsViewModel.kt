package ipca.example.dailynews.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PoliticsViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(ArticlesState())
    val uiState: StateFlow<ArticlesState> = _uiState

    fun fetchPoliticsArticles() {
        viewModelScope.launch {
            try {
                _uiState.value = _uiState.value.copy(isLoading = true)
                // Fazer chamada para buscar notícias de política
                val politicsArticles = repository.getArticlesByCategory("politics")
                _uiState.value = _uiState.value.copy(articles = politicsArticles, isLoading = false)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(error = e.message, isLoading = false)
            }
        }
    }
}

