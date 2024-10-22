package ipca.example.dailynews.ui

import androidx.compose.material3.BottomNavigation
import androidx.compose.material3.BottomNavigationItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.SportsSoccer
import androidx.compose.material.icons.filled.AccountBalance

@Composable
fun BottomNavBar(navController: NavController) {
    BottomNavigation {
        BottomNavigationItem(
            icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
            label = { Text("Home") },
            selected = false,  // You can manage the selection state here
            onClick = {
                navController.navigate("home")
            }
        )
        BottomNavigationItem(
            icon = { Icon(Icons.Default.SportsSoccer, contentDescription = "Desporto") },
            label = { Text("Desporto") },
            selected = false,
            onClick = {
                navController.navigate("sports")
            }
        )
        BottomNavigationItem(
            icon = { Icon(Icons.Default.AccountBalance, contentDescription = "Política") },
            label = { Text("Política") },
            selected = false,
            onClick = {
                navController.navigate("politics")
            }
        )
    }
}
