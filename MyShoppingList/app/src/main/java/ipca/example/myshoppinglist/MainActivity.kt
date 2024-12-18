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
import ipca.example.myshoppinglist.ui.lists.EditListView
import ipca.example.myshoppinglist.ui.lists.ShareListView
import ipca.example.myshoppinglist.ui.lists.items.ItemEditView
import ipca.example.myshoppinglist.ui.profile.ProfileView
import ipca.example.myshoppinglist.ui.login.LoginView
import ipca.example.myshoppinglist.ui.login.RegisterView
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
                        // Login View
                        composable(Screen.Login.route) {
                            LoginView(
                                modifier = Modifier.padding(innerPadding),
                                navController = navController,
                                onLoginSuccess = {
                                    navController.navigate(Screen.Home.route){
                                        launchSingleTop = true
                                        popUpTo(0) { inclusive = true}
                                    }
                                }
                            )
                        }

                        // Register View
                        composable(Screen.Register.route) {
                            RegisterView(navController = navController)
                        }

                        // Home View
                        composable(Screen.Home.route) {
                            ListListsView(navController = navController)
                        }

                        // Add List View
                        composable(Screen.AddList.route) {
                            AddListView(navController = navController)
                        }

                        // List Items View
                        composable(
                            route = Screen.ListItems.route,
                            arguments = listOf(navArgument("listId") { type = NavType.StringType })
                        ) { backStackEntry ->
                            val listId = backStackEntry.arguments?.getString("listId") ?: return@composable
                            ListItemsView(listId = listId, navController = navController)
                        }

                        // Edit Item View
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

                        // Profile View
                        composable(Screen.Profile.route) {
                            ProfileView(navController = navController)
                        }

                        // Edit List View
                        composable(
                            route = Screen.EditList.route,
                            arguments = listOf(navArgument("listId") { type = NavType.StringType })
                        ) { backStackEntry ->
                            val listId = backStackEntry.arguments?.getString("listId") ?: return@composable
                            EditListView(listId = listId, navController = navController)
                        }

                        // Share List View
                        composable(
                            route = Screen.ShareList.route,
                            arguments = listOf(navArgument("listId") { type = NavType.StringType })
                        ) { backStackEntry ->
                            val listId = backStackEntry.arguments?.getString("listId") ?: return@composable
                            ShareListView(listId = listId, navController = navController)
                        }
                    }
                }

                // Navigate to Home if user is logged in
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

// Sealed Class for Screen Definitions
sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Register : Screen("register")
    object Home : Screen("home")
    object AddList : Screen("add_list")
    object ListItems : Screen("list_items/{listId}") {
        fun createRoute(listId: String) = "list_items/$listId"
    }
    object EditItem : Screen("edit_item/{listId}/{itemId}") {
        fun createRoute(listId: String, itemId: String) = "edit_item/$listId/$itemId"
    }
    object Profile : Screen("profile")
    object EditList : Screen("edit_list/{listId}") {
        fun createRoute(listId: String) = "edit_list/$listId"
    }
    object ShareList : Screen("share_list/{listId}") {
        fun createRoute(listId: String) = "share_list/$listId"
    }
}
