package ipca.example.myshoppinglist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import android.util.Log

class ItemRowViewModel : ViewModel() {

    fun updateItemCheckedState(item: Item) {
        viewModelScope.launch {
            try {
                val db = Firebase.firestore
                val listId = item.listId ?: return@launch
                val itemId = item.docId ?: return@launch
                db.collection("lists").document(listId).collection("items").document(itemId)
                    .update("checked", item.checked).await()
            } catch (e: Exception) {
                Log.e("ItemRowViewModel", "Error updating item checked state: ${e.message}")
                e.printStackTrace()
            }
        }
    }
}

