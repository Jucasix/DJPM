package ipca.example.myshoppinglist.ui.lists.items

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import ipca.example.myshoppinglist.models.Item
import android.util.Log
import kotlinx.coroutines.tasks.await

data class ItemEditState(
    val item: Item? = null,
    val isLoading: Boolean = true,
    val error: String? = null
)

class ItemEditViewModel : ViewModel() {

    private val _state = MutableStateFlow(ItemEditState())
    val state: StateFlow<ItemEditState> get() = _state

    // Obter um item por ID
    fun getItemById(listId: String, itemId: String) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            try {
                val db = Firebase.firestore
                val document = db.collection("lists").document(listId)
                    .collection("items").document(itemId).get().await()
                val item = document.toObject(Item::class.java)?.apply {
                    this.docId = document.id
                    this.listId = listId
                }
                _state.update { it.copy(item = item, isLoading = false) }
            } catch (e: Exception) {
                Log.e("ItemEditViewModel", "Error fetching item: ${e.message}")
                _state.update { it.copy(error = e.message, isLoading = false) }
            }
        }
    }

    // Atualizar um item específico
    fun updateItem(item: Item) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            try {
                val db = Firebase.firestore
                val listId = item.listId ?: return@launch
                val itemId = item.docId ?: return@launch
                db.collection("lists").document(listId)
                    .collection("items").document(itemId)
                    .set(item).await()
                _state.update { it.copy(isLoading = false) }
            } catch (e: Exception) {
                Log.e("ItemEditViewModel", "Error updating item: ${e.message}")
                _state.update { it.copy(error = e.message, isLoading = false) }
            }
        }
    }

    // Eliminar um item específico
    fun deleteItem(item: Item) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            try {
                val db = Firebase.firestore
                val listId = item.listId ?: return@launch
                val itemId = item.docId ?: return@launch
                db.collection("lists").document(listId)
                    .collection("items").document(itemId)
                    .delete().await()
                _state.update { it.copy(isLoading = false) }
            } catch (e: Exception) {
                Log.e("ItemEditViewModel", "Error deleting item: ${e.message}")
                _state.update { it.copy(error = e.message, isLoading = false) }
            }
        }
    }
}
