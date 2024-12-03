package ipca.example.myshoppinglist.models

class Item(
    var docId: String?,
    var listId: String?,
    var name: String?,
    var qtd: Double?,
    var checked: Boolean = false
) {
    // Construtor secundário padrão
    constructor() : this(null, null, null, null, false)
}

