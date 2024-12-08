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
                val document = db.collection("lists").document(listId).get().await()
                val listName = document.getString("name") + " (Copy)"
                val newList = hashMapOf("name" to listName)
                db.collection("lists").add(newList).await()
            } catch (e: Exception) {
                _state.value = _state.value.copy(error = e.message)
            }
        }
    }
}
