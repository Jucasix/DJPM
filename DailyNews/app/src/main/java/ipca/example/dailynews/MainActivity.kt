package ipca.example.dailynews

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ipca.example.dailynews.ui.ArticleDetail
import ipca.example.dailynews.ui.BottomNavBar
import ipca.example.dailynews.ui.HomeView
import ipca.example.dailynews.ui.HomeViewModel
import ipca.example.dailynews.ui.theme.DailyNewsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DailyNewsTheme {
                val navController = rememberNavController()
                val homeViewModel: HomeViewModel = viewModel()

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        BottomNavBar(
                            navController = navController,
                            viewModel = homeViewModel
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
                        // Tela de detalhes do artigo
                        composable(route = Screen.ArticleDetail.route) { backStackEntry ->
                            val articleUrl = backStackEntry.arguments?.getString("articleUrl")
                            ArticleDetail(
                                articleUrl = articleUrl
                            )
                        }
                    }
                }
            }
        }
    }
}

// Configuração das rotas para navegação
sealed class Screen(val route: String) {
    object Home : Screen("Home")
    object ArticleDetail : Screen("article_detail/{articleUrl}")
}
