import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ipca.example.dailynews.database.FavoriteArticleDao
import ipca.example.dailynews.ui.FavoritesViewModel

class FavoritesViewModelFactory(
    private val dao: FavoriteArticleDao
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FavoritesViewModel::class.java)) {
            return FavoritesViewModel(dao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
