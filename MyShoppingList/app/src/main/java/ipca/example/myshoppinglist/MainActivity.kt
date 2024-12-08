package ipca.example.myshoppinglist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import ipca.example.myshoppinglist.ui.lists.items.ListItemsView
import ipca.example.myshoppinglist.ui.lists.AddListView
import ipca.example.myshoppinglist.ui.lists.ListListsView
import ipca.example.myshoppinglist.ui.lists.items.ItemEditView
import ipca.example.myshoppinglist.ui.profile.ProfileView
import ipca.example.myshoppinglist.ui.login.LoginView
import ipca.example.myshoppinglist.ui.theme.MyShoppingListTheme

const val TAG = "ShoppingList"

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyShoppingListTheme {
                val navController = rememberNavController()
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavHost(
                        modifier = Modifier.padding(innerPadding),
                        navController = navController,
                        startDestination = Screen.Login.route
                    ) {
                        composable(Screen.Login.route) {
                            LoginView(
                                modifier = Modifier.padding(innerPadding),
                                onLoginSuccess = {
                                    navController.navigate(Screen.Home.route)
                                }
                            )
                        }
                        composable(Screen.Home.route) {
                            ListListsView(
                                navController = navController
                            )
                        }
                        composable(Screen.AddList.route) {
                            AddListView(navController = navController)
                        }
                        composable(
                            route = Screen.ListItems.route,
                            arguments = listOf(navArgument("listId") {
                                type = NavType.StringType
                            })
                        ) { backStackEntry ->
                            val listId = backStackEntry.arguments?.getString("listId") ?: return@composable
                            ListItemsView(listId = listId, navController = navController)
                        }
                        composable(
                            route = Screen.EditItem.route,
                            arguments = listOf(
                                navArgument("listId") { type = NavType.StringType },
                                navArgument("itemId") { type = NavType.StringType }
                            )
                        ) { backStackEntry ->
                            val listId = backStackEntry.arguments?.getString("listId") ?: return@composable
                            val itemId = backStackEntry.arguments?.getString("itemId") ?: return@composable
                            ItemEditView(listId = listId, itemId = itemId, navController = navController)
                        }
                        composable(Screen.Profile.route) {
                            ProfileView(navController = navController)
                        }
                    }
                }
                LaunchedEffect(Unit) {
                    val auth = Firebase.auth
                    val currentUser = auth.currentUser
                    if (currentUser != null) {
                        navController.navigate(Screen.Home.route)
                    }
                }
            }
        }
    }
}

sealed class Screen(val route: String) {
    object ListItems : Screen("list_items/{listId}")
    object EditItem : Screen("edit_item/{listId}/{itemId}")
    object Login : Screen("login")
    object Home : Screen("home")
    object AddList : Screen("add_list")
    object Profile : Screen("profile")
}
