package ipca.example.myshoppinglist.ui.lists.items

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.*
import androidx.compose.runtime.remember
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

@Composable
fun ListItemsView(
    listId: String,
    modifier: Modifier = Modifier,
    navController: NavController = rememberNavController()
) {
    val viewModel: ListItemsViewModel = viewModel()
    val state by viewModel.state.collectAsState()

    val newItemName = remember { mutableStateOf("") }

    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomEnd
    ) {
        Column(modifier = modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "Items in ${state.listName ?: "List"}",
                fontSize = 24.sp,
                modifier = Modifier.padding(16.dp)
            )

            LazyColumn(modifier = modifier.fillMaxSize().weight(1f)) {
                itemsIndexed(
                    items = state.items
                ) { _, item ->
                    ItemRowView(item = item, navController = navController)
                }
            }

            val newItemQuantity = remember { mutableStateOf("") }

            Row(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
                TextField(
                    value = newItemName.value,
                    onValueChange = { newItemName.value = it },
                    placeholder = { Text("New Item") },
                    modifier = Modifier.weight(3f).padding(end = 8.dp)
                )

                TextField(
                    value = newItemQuantity.value,
                    onValueChange = { newItemQuantity.value = it },
                    placeholder = { Text("Qt") },
                    modifier = Modifier.weight(1f)
                )
            }

            Row(modifier = Modifier.fillMaxWidth().padding(16.dp), horizontalArrangement = Arrangement.SpaceEvenly) {
                Button(
                    modifier = Modifier.weight(1f).size(48.dp),
                    onClick = {
                        navController.navigate(Screen.Home.route) {
                            popUpTo(Screen.Home.route) { inclusive = true }
                        }
                    }
                ) {
                    Icon(painter = painterResource(R.drawable.baseline_home_24), contentDescription = "Home")
                }
                Button(
                    modifier = Modifier.weight(1f).size(48.dp),
                    onClick = {
                        val quantity = newItemQuantity.value.toDoubleOrNull() ?: 1.0
                        viewModel.addItem(listId, newItemName.value, quantity)
                        newItemName.value = ""
                        newItemQuantity.value = ""
                    }
                ) {
                    Text("Add Item")
                }
            }
        }
    }

    LaunchedEffect(key1 = true) {
        viewModel.getListName(listId)
        viewModel.getItems(listId)
    }
}


