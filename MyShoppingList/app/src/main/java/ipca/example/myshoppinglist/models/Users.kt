package ipca.example.myshoppinglist.models

class Users(
    var userId: String? = null, // ID único do utilizador (Firebase UID)
    var email: String? = null,  // Email associado ao utilizador
    var name: String? = null    // Nome do utilizador
) {
    // Construtor vazio necessário para o Firebase
    constructor() : this(null, null, null)
}
