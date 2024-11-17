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

@Composable
fun BottomNavBar(navController: NavController, viewModel: HomeViewModel = viewModel()) {
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
                            // Limpa o filtro e mostra todas as notícias
                            viewModel.setCategoryAndFetch("") // Atualiza a categoria para "Home"
                            // Não navega, apenas limpa o filtro de notícias
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

                // Botão Sports
                Surface(
                    modifier = Modifier
                        .padding(8.dp)
                        .width(80.dp)
                        .clickable {
                            viewModel.setCategoryAndFetch("sports") // Atualiza a categoria para "Sports"
                        },
                    shape = RoundedCornerShape(8.dp),
                    color = MaterialTheme.colorScheme.primary
                ) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.padding(12.dp)
                    ) {
                        Text("Sports", color = Color.White)
                    }
                }

                // Botão Politics
                Surface(
                    modifier = Modifier
                        .padding(8.dp)
                        .width(80.dp)
                        .clickable {
                            viewModel.setCategoryAndFetch("politics") // Atualiza a categoria para "Politics"
                        },
                    shape = RoundedCornerShape(8.dp),
                    color = MaterialTheme.colorScheme.primary
                ) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.padding(12.dp)
                    ) {
                        Text("Politics", color = Color.White)
                    }
                }
            }
        }
    )
}
