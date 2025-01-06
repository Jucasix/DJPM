package ipca.example.dailynews

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import ipca.example.dailynews.ui.ArticleDetail
import ipca.example.dailynews.ui.BottomNavBar
import ipca.example.dailynews.ui.FavoritesView
import ipca.example.dailynews.ui.HomeView
import ipca.example.dailynews.ui.HomeViewModel
import ipca.example.dailynews.ui.theme.DailyNewsTheme
import ipca.example.dailynews.database.AppDatabase
import ipca.example.dailynews.ui.FavoritesViewModel
import ipca.example.dailynews.ui.FavoritesViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DailyNewsTheme {
                val navController = rememberNavController()
                val homeViewModel: HomeViewModel = viewModel()

                // Inicialização do database e DAO
                val database = AppDatabase.getDatabase(this) // Certifique-se de que o AppDatabase está implementado corretamente
                val favoritesDao = database.favoriteArticleDao()

                // Inicialização do FavoritesViewModel com o Factory
                val favoritesViewModel: FavoritesViewModel = ViewModelProvider(
                    this,
                    FavoritesViewModelFactory(favoritesDao)
                ).get(FavoritesViewModel::class.java)

                // Obtem a rota atual para passar ao BottomNavBar
                val currentRoute =
                    navController.currentBackStackEntryAsState()?.value?.destination?.route
                        ?: Screen.Home.route

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        BottomNavBar(
                            navController = navController,
                            currentRoute = currentRoute
                        )
                    }
                ) { innerPadding ->
                    // Configuração do NavHost para navegação
                    NavHost(
                        navController = navController,
                        startDestination = Screen.Home.route,
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        // Tela Home
                        composable(route = Screen.Home.route) {
                            HomeView(
                                modifier = Modifier.padding(innerPadding),
                                navController = navController
                            )
                        }

                        composable(route = Screen.Favorites.route) {
                            FavoritesView(
                                homeViewModel = homeViewModel,
                                favoritesViewModel = favoritesViewModel,
                                navController = navController
                            )
                        }

                        composable(route = Screen.ArticleDetail.route) { backStackEntry ->
                            val articleUrl = backStackEntry.arguments?.getString("articleUrl")
                            ArticleDetail(
                                articleUrl = articleUrl,
                                favoritesViewModel = favoritesViewModel
                            )
                        }
                    }
                }
            }
        }
    }
}

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object ArticleDetail : Screen("article_detail/{articleUrl}")
    object Favorites : Screen("favorites")
}
