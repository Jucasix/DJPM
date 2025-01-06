package ipca.example.dailynews.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import ipca.example.dailynews.Screen

@Composable
fun BottomNavBar(
    navController: NavController,
    currentRoute: String
) {
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        when (currentRoute) {
            Screen.Home.route -> {
                androidx.compose.material3.Button(onClick = {
                    navController.navigate(Screen.Favorites.route) {
                        popUpTo(Screen.Favorites.route) { inclusive = true }
                    }
                }) {
                    Text("Favorites")
                }
            }
            Screen.ArticleDetail.route -> {
                androidx.compose.material3.Button(onClick = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Home.route) { inclusive = true }
                    }
                }) {
                    Text("Home")
                }
            }
            Screen.Favorites.route -> {
                androidx.compose.material3.Button(onClick = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Home.route) { inclusive = true }
                    }
                }) {
                    Text("Home")
                }
            }
        }
    }
}
