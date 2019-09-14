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
        val charList: MutableList<Char> = mutableListOf()
        charList += ('a'..'z')
        charList += ('A'..'Z')
        charList += ('0'..'9')
        val password = (1..length)
            .map { charList.random() }
            .joinToString("")
        view?.setPassword(password)
    }

    fun createPassword(passLength: Int, array: BooleanArray) {
        val length = if (array[0]) Random.nextInt(7, 13) else passLength
        val charList: MutableList<Char> = mutableListOf()
        if (array[1]) charList += ('A'..'Z')
        if (array[2]) charList += ('a'..'z')
        if (array[3]) charList += ('0'..'9')
        val password = (1..length)
            .map { charList.random() }
            .joinToString("")
        view?.setPassword(password)
    }

    fun detach() {
        view = null
    }
}