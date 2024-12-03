package ipca.example.myshoppinglist.ui.lists.items

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import ipca.example.myshoppinglist.R
import ipca.example.myshoppinglist.models.Item
import androidx.navigation.NavController

@Composable
fun ItemRowView(item: Item, navController: NavController) {
    val viewModel: ItemRowViewModel = viewModel()
    val isChecked = remember { mutableStateOf(item.checked) }
    val quantity = item.qtd ?: 1.0

    Row(
        modifier = Modifier.padding(8.dp).fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = item.name ?: "Unnamed Item",
            fontSize = 18.sp,
            modifier = Modifier.padding(start = 8.dp).weight(1f)
        )
        Text(
            text = "Qtd: $quantity",
            fontSize = 18.sp,
            modifier = Modifier.padding(start = 16.dp).weight(1f)
        )
        Checkbox(
            checked = isChecked.value,
            onCheckedChange = {
                isChecked.value = it
                item.checked = it
                viewModel.updateItemCheckedState(item)
            },
            modifier = Modifier.size(24.dp)
        )
        Button(
            onClick = {
                navController.navigate("edit_item/${item.listId}/${item.docId}")
            }
        ){
            Icon(
                painter = painterResource(R.drawable.baseline_pending_24),
                contentDescription = "Edit Item"
            )
        }
    }
}
