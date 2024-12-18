package ipca.example.myshoppinglist.ui.lists

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import ipca.example.myshoppinglist.R
import ipca.example.myshoppinglist.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditListView(
    listId: String,
    navController: NavController = rememberNavController()
) {
    val viewModel: EditListViewModel = viewModel()
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadList(listId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Edit List", fontSize = 20.sp) }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Campo para editar o nome da lista
            TextField(
                value = state.listName ?: "",
                onValueChange = { viewModel.updateListName(it) },
                label = { Text("List Name") },
                modifier = Modifier.fillMaxWidth()
            )

            // Botão para salvar alterações
            Button(
                onClick = {
                    viewModel.saveListName(listId)
                    navController.popBackStack()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    painter = painterResource(R.drawable.baseline_check_24),
                    contentDescription = "Save List"
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Save")
            }

            // Botão para duplicar a lista
            Button(
                onClick = {
                    viewModel.duplicateList(listId)
                    navController.popBackStack()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    painter = painterResource(R.drawable.baseline_content_copy_24),
                    contentDescription = "Duplicate List"
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Duplicate")
            }

            // Botão para apagar a lista
            Button(
                onClick = {
                    viewModel.deleteList(listId)
                    navController.popBackStack()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    painter = painterResource(R.drawable.baseline_delete_24),
                    contentDescription = "Delete List"
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Delete")
            }

            // Botão para partilhar a lista
            Button(
                onClick = {
                    navController.navigate(Screen.ShareList.createRoute(listId))
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    painter = painterResource(R.drawable.baseline_share_24),
                    contentDescription = "Share List"
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Share")
            }
        }
    }
}
