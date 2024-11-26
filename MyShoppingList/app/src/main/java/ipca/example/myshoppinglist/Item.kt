package ipca.example.myshoppinglist

class Item(
    var docId: String?,
    var listId: String?, // Adicionar listId ao item
    var name: String?,
    var qtd: Double?,
    var checked: Boolean = false
) {
    constructor() : this(null, null, null, null, false)
}
