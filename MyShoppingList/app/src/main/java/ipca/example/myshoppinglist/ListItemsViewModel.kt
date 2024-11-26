package ipca.example.myshoppinglist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

data class ListItemsState(
    val listName: String = "",
    val items: List<Item> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

class ListItemsViewModel : ViewModel() {

    private val _state = MutableStateFlow(ListItemsState())
    val state: StateFlow<ListItemsState> get() = _state.asStateFlow()

    fun getItems(listId: String) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            try {
                val fetchedItems = fetchItemsFromFirebase(listId)
                _state.update { it.copy(items = fetchedItems, isLoading = false) }
            } catch (e: Exception) {
                _state.update { it.copy(error = e.message, isLoading = false) }
            }
        }
    }

    fun addItem(listId: String, itemName: String, quantity: Double) {
        if (itemName.isBlank()) return

        viewModelScope.launch {
            try {
                // TODO: Add logic to add item to Firebase under the specified listId
                addItemToFirebase(listId, itemName, quantity)
                val updatedItems = _state.value.items + Item(docId = null, name = itemName, qtd = quantity, checked = false)
                _state.update { it.copy(items = updatedItems) }
            } catch (e: Exception) {
                _state.update { it.copy(error = e.message) }
            }
        }
    }

    fun getListName(listId: String) {
        viewModelScope.launch {
            try {
                val db = Firebase.firestore
                val document = db.collection("lists").document(listId).get().await()
                val listName = document.getString("name") ?: "List"
                _state.update { it.copy(listName = listName) }
            } catch (e: Exception) {
                _state.update { it.copy(error = e.message) }
            }
        }
    }

    private suspend fun fetchItemsFromFirebase(listId: String): List<Item> {
        val items = mutableListOf<Item>()
        try {
            val db = Firebase.firestore
            val result = db.collection("lists").document(listId).collection("items").get().await()
            for (document in result.documents) {
                val item = document.toObject(Item::class.java)
                item?.let { items.add(it) }
            }
        } catch (e: Exception) {
            _state.update { it.copy(error = e.message) }
        }
        return items
    }

    private suspend fun addItemToFirebase(listId: String, itemName: String, quantity: Double) {
        try {
            val db = Firebase.firestore
            val newItem = Item(null, itemName, quantity, false)
            db.collection("lists")
                .document(listId)
                .collection("items")
                .add(newItem)
                .await()
        } catch (e: Exception) {
            _state.update { it.copy(error = e.message) }
        }
    }
}

