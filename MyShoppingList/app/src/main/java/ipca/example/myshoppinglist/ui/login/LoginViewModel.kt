package ipca.example.myshoppinglist.ui.login

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import ipca.example.myshoppinglist.TAG
import ipca.example.myshoppinglist.repositories.UsersRepository
import kotlinx.coroutines.launch

data class LoginState(
    val email: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val error: String? = null,
    val successMessage: String? = null
)

class LoginViewModel : ViewModel() {

    var state = mutableStateOf(LoginState())
        private set

    private val usersRepository = UsersRepository()

    fun onEmailChange(email: String) {
        state.value = state.value.copy(email = email)
    }

    fun onPasswordChange(password: String) {
        state.value = state.value.copy(password = password)
    }

    fun onLoginClick(onLoginSuccess: () -> Unit) {
        state.value = state.value.copy(isLoading = true)

        val auth: FirebaseAuth = Firebase.auth

        auth.signInWithEmailAndPassword(state.value.email, state.value.password)
            .addOnCompleteListener { task ->
                state.value = state.value.copy(isLoading = false)
                if (task.isSuccessful) {
                    Log.d(TAG, "signInWithEmail:success")
                    onLoginSuccess()
                } else {
                    Log.w(TAG, "signInWithEmail:failure", task.exception)
                    state.value = state.value.copy(error = task.exception?.message ?: "Unknown error")
                }
            }
    }

    fun onResetPasswordClick() {
        val email = state.value.email
        if (email.isNotBlank()) {
            viewModelScope.launch {
                val result = usersRepository.resetPassword(email)
                result.fold(
                    onSuccess = {
                        state.value = state.value.copy(successMessage = "Password reset email sent to $email")
                    },
                    onFailure = { e ->
                        state.value = state.value.copy(error = e.message ?: "An error occurred")
                    }
                )
            }
        } else {
            state.value = state.value.copy(error = "Please enter your email address")
        }
    }
}
