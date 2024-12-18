package ipca.example.myshoppinglist.ui.lists

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

data class EditListState(
    val listName: String? = null,
    val error: String? = null
)

class EditListViewModel : ViewModel() {
    private val _state = MutableStateFlow(EditListState())
    val state: StateFlow<EditListState> get() = _state

    fun loadList(listId: String) {
        viewModelScope.launch {
            try {
                val db = Firebase.firestore
                val document = db.collection("lists").document(listId).get().await()
                val listName = document.getString("name")
                _state.value = _state.value.copy(listName = listName)
            } catch (e: Exception) {
                _state.value = _state.value.copy(error = e.message)
            }
        }
    }

    fun updateListName(newName: String) {
        _state.value = _state.value.copy(listName = newName)
    }

    fun saveListName(listId: String) {
        viewModelScope.launch {
            try {
                val db = Firebase.firestore
                db.collection("lists").document(listId).update("name", _state.value.listName).await()
            } catch (e: Exception) {
                _state.value = _state.value.copy(error = e.message)
            }
        }
    }

    fun deleteList(listId: String) {
        viewModelScope.launch {
            try {
                val db = Firebase.firestore
                db.collection("lists").document(listId).delete().await()
            } catch (e: Exception) {
                _state.value = _state.value.copy(error = e.message)
            }
        }
    }

    fun duplicateList(listId: String) {
        viewModelScope.launch {
            try {
                val db = Firebase.firestore
                val auth = com.google.firebase.auth.FirebaseAuth.getInstance()
                val userId = auth.currentUser?.uid ?: return@launch

                // Passo 1: Buscar a lista original
                val originalList = db.collection("lists").document(listId).get().await()
                val originalName = originalList.getString("name") ?: "Untitled List"

                // Criar a nova lista com o nome copiado e apenas o user atual no owners
                val newListData = hashMapOf(
                    "name" to "$originalName (Copy)",
                    "owners" to listOf(userId)
                )

                // Adicionar a nova lista e obter o ID
                val newListRef = db.collection("lists").add(newListData).await()
                val newListId = newListRef.id

                // Passo 2: Buscar os itens da lista original
                val originalItems = db.collection("lists").document(listId)
                    .collection("items").get().await()

                // Passo 3: Iterar por TODOS os itens da lista original e copiá-los
                originalItems.documents.forEach { document ->
                    val itemData = document.data?.toMutableMap() ?: return@forEach
                    itemData["listId"] = newListId // Associar ao novo listId
                    db.collection("lists").document(newListId)
                        .collection("items").add(itemData).await()
                }

                // Resetar erros e informar sucesso
                _state.value = _state.value.copy(error = null)
            } catch (e: Exception) {
                _state.value = _state.value.copy(error = e.message)
            }
        }
    }
}
