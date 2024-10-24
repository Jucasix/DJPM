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
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import ipca.example.dailynews.Screen

@Composable
fun BottomNavBar(navController: NavController) {
    val currentBackStackEntry = navController.currentBackStackEntryAsState()
    val currentDestination = currentBackStackEntry.value?.destination?.route

    BottomAppBar(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(horizontal = 16.dp),
        content = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                // Botão Home
                Surface(
                    modifier = Modifier
                        .padding(8.dp)
                        .width(80.dp)
                        .clickable {
                            // Navegação para a página Home da sua aplicação
                            if (currentDestination != Screen.Home.route) {
                                navController.navigate(Screen.Home.route) {
                                    // Limpa o back stack para evitar voltar à tela anterior
                                    popUpTo(navController.graph.startDestinationId) {
                                        inclusive = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        },
                    shape = RoundedCornerShape(8.dp),
                    color = MaterialTheme.colorScheme.primary
                ) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.padding(12.dp)
                    ) {
                        Text("Home", color = Color.White)
                    }
                }

                // Botão Sport
                Surface(
                    modifier = Modifier
                        .padding(8.dp)
                        .width(80.dp)
                        .clickable {
                            // Placeholder para futura ação de navegação
                        },
                    shape = RoundedCornerShape(8.dp),
                    color = MaterialTheme.colorScheme.primary
                ) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.padding(12.dp)
                    ) {
                        Text("Sport", color = Color.White)
                    }
                }

                // Botão Politic
                Surface(
                    modifier = Modifier
                        .padding(8.dp)
                        .width(80.dp)
                        .clickable {
                            // Placeholder para futura ação de navegação
                        },
                    shape = RoundedCornerShape(8.dp),
                    color = MaterialTheme.colorScheme.primary
                ) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.padding(12.dp)
                    ) {
                        Text("Politic", color = Color.White)
                    }
                }
            }
        }
    )
}
