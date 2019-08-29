package by.letum8658.passwordwallet

object ItemManager {

    private var itemList: MutableList<Item> = mutableListOf()

    init {
        for (i in 1..11) {
            itemList.add(Item("Item $i", "Password", i))
        }
    }

    fun getItemList(): MutableList<Item> = itemList

    fun getItemById(id: Int): Item? = itemList.find { it.id == id }

    fun deleteItem(item: Item) {
        itemList.remove(item)
    }

    fun updateItem(newItem: Item) {
        val id = newItem.id
        val oldItem = itemList.find { it.id == id }
        val index = itemList.indexOf(oldItem)
        itemList[index] = newItem
    }

    fun addNewItem(item: Item) {
        itemList.add(item)
    }

    fun getSearchList(string: String): List<Item> = itemList.filter { it.name.contains(string, true) }
}