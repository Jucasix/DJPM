package ipca.example.myshoppinglist.ui.lists.items

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import ipca.example.myshoppinglist.R
import ipca.example.myshoppinglist.models.Item
import ipca.example.myshoppinglist.ui.theme.MyShoppingListTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItemEditView(
    listId: String,
    itemId: String,
    navController: NavController = rememberNavController()
) {
    val viewModel: ItemEditViewModel = viewModel()
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getItemById(listId, itemId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Edit Item",
                            color = Color(0xFF495D92),
                            fontSize = 20.sp
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        state.item?.let {
            var itemName by remember { mutableStateOf(it.name ?: "") }
            var itemQuantity by remember { mutableStateOf(it.qtd?.toString() ?: "1.0") }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(16.dp)
            ) {
                TextField(
                    value = itemName,
                    onValueChange = { itemName = it },
                    label = { Text("Item Name") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                TextField(
                    value = itemQuantity,
                    onValueChange = { itemQuantity = it },
                    label = { Text("Quantity") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Button(
                        onClick = {
                            viewModel.deleteItem(it)
                            navController.popBackStack()
                        },
                        modifier = Modifier.padding(end = 8.dp)
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.baseline_delete_24),
                            contentDescription = "Delete Item"
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Delete")
                    }

                    Button(
                        onClick = {
                            val updatedItem = Item(
                                docId = it.docId,
                                listId = it.listId,
                                name = itemName,
                                qtd = itemQuantity.toDoubleOrNull() ?: 1.0,
                                checked = it.checked
                            )
                            viewModel.updateItem(updatedItem)
                            navController.popBackStack()
                        }
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.baseline_check_24),
                            contentDescription = "Save Item"
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Save")
                    }
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun ItemEditViewPreview() {
    MyShoppingListTheme {
        val iti = "P7niuMu4pAi6zKSbW1Jl"
        val itl = "Gw1RJYZm2xaYkIIBER4X"
        ItemEditView(listId = itl, itemId = iti)
    }
}
