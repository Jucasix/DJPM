package ipca.example.myshoppinglist.ui.lists

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import ipca.example.myshoppinglist.TAG
import ipca.example.myshoppinglist.models.ListItems

// Define o estado da lista de compras
data class ListListsState(
    val listItemsList: List<ListItems> = arrayListOf(),
    val isLoading: Boolean = false,
    val error: String? = null
)

// ViewModel para gerir a lista de compras
class ListListsViewModel : ViewModel() {

    var state = mutableStateOf(ListListsState())
        private set

    fun getLists() {
        val db = Firebase.firestore
        val auth = Firebase.auth
        val currentUser = auth.currentUser
        val userId = currentUser?.uid

        Log.d(TAG, "Fetching lists for userId: $userId") // Debug do userId

        // Verificar se o userId não é nulo
        if (userId != null) {
            db.collection("lists")
                .whereArrayContains("owners", userId)
                .get()
                .addOnSuccessListener { documents ->
                    val listItemsList = arrayListOf<ListItems>()
                    for (document in documents) {
                        Log.d(TAG, "${document.id} => ${document.data}") // Debug de cada documento
                        val listItem = document.toObject(ListItems::class.java)
                        listItem.docId = document.id
                        listItemsList.add(listItem)
                    }
                    Log.d(TAG, "Fetched ${listItemsList.size} lists for userId: $userId") // Tamanho da lista
                    state.value = state.value.copy(
                        listItemsList = listItemsList,
                        isLoading = false
                    )
                }
                .addOnFailureListener { exception ->
                    Log.w(TAG, "Error getting documents: ", exception)
                    state.value = state.value.copy(
                        error = exception.localizedMessage,
                        isLoading = false
                    )
                }
        } else {
            Log.w(TAG, "UserId is null. Cannot fetch lists.")
        }
    }
}
