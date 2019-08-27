package by.letum8658.passwordwallet.presenter

import by.letum8658.passwordwallet.Callback
import by.letum8658.passwordwallet.Item
import by.letum8658.passwordwallet.ItemManager
import by.letum8658.passwordwallet.utils.encode

class CreateItemPresenter {

    private var view: CreateItemView? = null

    fun setView(view: CreateItemView?) {
        this.view = view
    }

    fun setData(list: ArrayList<String>?) {
        if (list != null) {
            if (list[0].isNotBlank()) {
                view?.setName(list[0])
            }
            if (list[1].isNotBlank()) {
                view?.setPassword(list[1])
                view?.setConfirmPassword(list[1])
            }
        }
    }

    fun createPassword() {
        val name = view?.getName()
        view?.createPassword(name!!)
    }

    fun saveItem() {
        val itemName = view?.getName()!!
        val password = view?.getPassword()!!
        val confirm = view?.getConfirmPassword()
        if (itemName.isNotBlank()) {
            if (password == confirm) {
                val account = ItemManager.getName()!!
                val cryptPassword = encode(password)
                val list = ItemManager.getItemList()
                if (list.contains(itemName)) {
                    view?.progressBarOn()
                    ItemManager.updateItem(
                        account,
                        itemName,
                        Item(cryptPassword),
                        object : Callback() {
                            override fun returnResult(text: String?) {
                                view?.progressBarOff()
                                view?.saveItem()
                            }
                        })
                } else {
                    list.add(itemName)
                    ItemManager.setItemList(list)
                    ItemManager.updateAllNames(account, list)
                    view?.progressBarOn()
                    ItemManager.saveNewItem(
                        account,
                        itemName,
                        Item(cryptPassword),
                        object : Callback() {
                            override fun returnResult(text: String?) {
                                view?.progressBarOff()
                                view?.saveItem()
                            }
                        })
                }
            } else {
                view?.showMessage(1)
            }
        } else {
            view?.showMessage(2)
        }
    }

    fun detach() {
        ItemManager.dispose()
        view = null
    }
}