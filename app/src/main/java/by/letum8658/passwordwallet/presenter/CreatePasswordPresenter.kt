package by.letum8658.passwordwallet.presenter

class CreatePasswordPresenter {

    private var view: CreatePasswordView? = null

    fun setView(view: CreatePasswordView?) {
        this.view = view
    }

    fun createPassword() {
        val password = "PASSWORD"
        view?.setPassword(password)
    }

    fun savePassword(name: String) {
        val password = view?.getPassword()
        val list = ArrayList<String>()
        list.add(name)
        list.add(password!!)
        view?.savePassword(list)
    }
}