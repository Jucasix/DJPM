package ipca.example.myshoppinglist.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import ipca.example.myshoppinglist.repositories.UsersRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class ProfileState(
    val email: String? = null,
    val name: String? = null,
    val error: String? = null,
    val successMessage: String? = null
)

class ProfileViewModel : ViewModel() {

    private val _state = MutableStateFlow(ProfileState())
    val state: StateFlow<ProfileState> = _state

    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()
    private val userId = auth.currentUser?.uid

    private val usersRepository = UsersRepository() // Repositório adicionado

    init {
        loadUserProfile()
    }

    private fun loadUserProfile() {
        val user = auth.currentUser
        _state.update { it.copy(email = user?.email) }

        userId?.let { uid ->
            viewModelScope.launch {
                try {
                    db.collection("users").document(uid).get()
                        .addOnSuccessListener { document ->
                            val name = document.getString("name")
                            _state.update { it.copy(name = name) }
                        }
                        .addOnFailureListener { e ->
                            _state.update { it.copy(error = e.message) }
                        }
                } catch (e: Exception) {
                    _state.update { it.copy(error = e.message) }
                }
            }
        }
    }

    fun updateName(newName: String) {
        userId?.let { uid ->
            viewModelScope.launch {
                val result = usersRepository.updateName(uid, newName)
                result.fold(
                    onSuccess = {
                        _state.update { it.copy(name = newName, successMessage = "Name updated successfully") }
                    },
                    onFailure = { e ->
                        _state.update { it.copy(error = e.message) }
                    }
                )
            }
        }
    }

    fun resetPassword() {
        val email = auth.currentUser?.email
        if (email != null) {
            viewModelScope.launch {
                val result = usersRepository.resetPassword(email)
                result.fold(
                    onSuccess = {
                        _state.update { it.copy(successMessage = "Password reset email sent to $email") }
                    },
                    onFailure = { e ->
                        _state.update { it.copy(error = e.message) }
                    }
                )
            }
        } else {
            _state.update { it.copy(error = "No email associated with this account.") }
        }
    }

    fun logout() {
        viewModelScope.launch {
            try {
                auth.signOut()
                _state.update { it.copy(email = null, name = null) }
            } catch (e: Exception) {
                _state.update { it.copy(error = e.message) }
            }
        }
    }
}
