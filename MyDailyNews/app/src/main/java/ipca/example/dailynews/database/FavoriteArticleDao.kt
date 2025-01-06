package ipca.example.dailynews.database

import androidx.room.*
import ipca.example.dailynews.models.FavoriteArticle

@Dao
interface FavoriteArticleDao {
    @Query("SELECT * FROM favorite_articles")
    fun getAllFavorites(): List<FavoriteArticle>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFavorite(article: FavoriteArticle)

    @Delete
    fun deleteFavorite(article: FavoriteArticle)

    @Query("SELECT * FROM favorite_articles WHERE url = :url LIMIT 1")
    fun getFavoriteByUrl(url: String): FavoriteArticle?
}
