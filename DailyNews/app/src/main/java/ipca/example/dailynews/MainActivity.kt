package ipca.example.dailynews

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
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
                    bottomBar = { BottomNavBar(navController = navController, viewModel = homeViewModel) } // Passa o ViewModel para a BottomNavBar
                ) { innerPadding ->
                    HomeView(modifier = Modifier.padding(innerPadding), navController = navController)
                }
            }
        }
    }
}




sealed class Screen(val route: String) {
    object Home : Screen("Home")
    object ArticleDetail : Screen("article_detail/{articleUrl}")

}

