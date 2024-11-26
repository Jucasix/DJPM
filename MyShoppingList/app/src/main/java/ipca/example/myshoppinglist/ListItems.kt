package ipca.example.myshoppinglist

data class ListItems (

    var name : String?,
    var owners : List<String>?) {

    constructor() : this(null,null)


    var docId : String? = null
}