package ipca.example.myshoppinglist.ui.login

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import ipca.example.myshoppinglist.models.Users

data class RegisterState(
    val name: String = "",
    val email: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val error: String? = null
)

class RegisterViewModel : ViewModel() {

    private val _state = MutableStateFlow(RegisterState())
    val state: StateFlow<RegisterState> = _state

    fun onNameChange(newName: String) {
        _state.value = _state.value.copy(name = newName)
    }

    fun onEmailChange(newEmail: String) {
        _state.value = _state.value.copy(email = newEmail)
    }

    fun onPasswordChange(newPassword: String) {
        _state.value = _state.value.copy(password = newPassword)
    }

    fun registerUser(onSuccess: () -> Unit, onFailure: (String) -> Unit) {
        val state = _state.value

        if (state.name.isBlank() || state.email.isBlank() || state.password.isBlank()) {
            _state.value = _state.value.copy(error = "All fields are required")
            return
        }

        _state.value = _state.value.copy(isLoading = true)

        Firebase.auth.createUserWithEmailAndPassword(state.email, state.password)
            .addOnSuccessListener { authResult ->
                val userId = authResult.user?.uid ?: return@addOnSuccessListener
                val user = Users(userId = userId, email = state.email, name = state.name)

                Firebase.firestore.collection("users").document(userId)
                    .set(user)
                    .addOnSuccessListener {
                        _state.value = _state.value.copy(isLoading = false)
                        onSuccess()
                    }
                    .addOnFailureListener { e ->
                        _state.value = _state.value.copy(isLoading = false, error = e.message)
                        onFailure(e.message ?: "Error saving user data")
                    }
            }
            .addOnFailureListener { e ->
                _state.value = _state.value.copy(isLoading = false, error = e.message)
                onFailure(e.message ?: "Error registering user")
            }
    }
}
