package ipca.example.myshoppinglist.ui.lists

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FieldValue
import ipca.example.myshoppinglist.models.Users
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

data class ShareListState(
    val searchQuery: String = "",
    val users: List<Users> = emptyList(),
    val filteredUsers: List<Users> = emptyList()
)

class ShareListViewModel : ViewModel() {
    private val _state = MutableStateFlow(ShareListState())
    val state: StateFlow<ShareListState> = _state

    private val db = FirebaseFirestore.getInstance()

    fun loadUsers() {
        viewModelScope.launch {
            try {
                db.collection("users").get().addOnSuccessListener { result ->
                    val users = result.map { document ->
                        Users(
                            userId = document.id,
                            email = document.getString("email"),
                            name = document.getString("name")
                        )
                    }
                    _state.update { it.copy(users = users, filteredUsers = users) }
                }
            } catch (e: Exception) {
                // Tratamento de erro
            }
        }
    }

    fun updateSearchQuery(query: String) {
        _state.update { currentState ->
            val filtered = currentState.users.filter {
                it.name?.contains(query, ignoreCase = true) == true
            }
            currentState.copy(searchQuery = query, filteredUsers = filtered)
        }
    }

    fun shareListWithUser(listId: String, userId: String) {
        viewModelScope.launch {
            try {
                db.collection("lists").document(listId).update(
                    "owners", FieldValue.arrayUnion(userId)
                )
            } catch (e: Exception) {
                // Tratamento de erro
            }
        }
    }
}
