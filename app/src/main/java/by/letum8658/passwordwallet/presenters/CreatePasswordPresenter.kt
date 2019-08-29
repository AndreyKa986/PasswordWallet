package by.letum8658.passwordwallet.presenters

import by.letum8658.passwordwallet.view.views.CreatePasswordView
import kotlin.random.Random

class CreatePasswordPresenter {

    private var view: CreatePasswordView? = null

    fun setView(view: CreatePasswordView?) {
        this.view = view
    }

    fun createPassword() {
        val length = Random.nextInt(7, 13)
        val charList = ('a'..'z') + ('A'..'Z') + ('0'..'9')
        val password = (1..length)
            .map { charList.random() }
            .joinToString("")
        view?.setPassword(password)
    }
    fun savePassword(name: String) {
        val password = view?.getPassword()!!
        val list = ArrayList<String>()
        list.add(name)
        list.add(password)
        view?.savePassword(list)
    }

    fun detach() {
        view = null
    }
}