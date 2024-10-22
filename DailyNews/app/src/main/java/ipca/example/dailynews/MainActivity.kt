package ipca.example.dailynews

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ipca.example.dailynews.ui.HomeView
import ipca.example.dailynews.ui.SportsView
import ipca.example.dailynews.ui.PoliticsView
import ipca.example.dailynews.ui.BottomNavBar
import ipca.example.dailynews.ui.theme.DailyNewsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DailyNewsTheme {
                val navController = rememberNavController()
                MainScreen(navController = navController)
            }
        }
    }
}

@Composable
fun MainScreen(navController: NavHostController) {
    Scaffold(
        bottomBar = { BottomNavBar(navController = navController) }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "home",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("home") { HomeView(navController = navController) }
            composable("sports") { SportsView(navController = navController) }
            composable("politics") { PoliticsView(navController = navController) }
        }
    }
}

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object ArticleDetail : Screen("article_detail/{articleUrl}")

}

