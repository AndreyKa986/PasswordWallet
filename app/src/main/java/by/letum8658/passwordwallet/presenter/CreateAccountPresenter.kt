package by.letum8658.passwordwallet.presenter

class CreateAccountPresenter {

    private var view: CreateAccountView? = null

    fun setView(view: CreateAccountView?) {
        this.view = view
    }

    fun createAccount() {
        if (isPasswordEqual()) {
            view?.createAccount(
                view?.getName()!!,
                view?.getPassword()!!
            )
        } else view?.showMessage()
    }

    private fun isPasswordEqual(): Boolean {
        val password = view?.getPassword()
        val confirmPassword = view?.getConfirmPassword()
        return password == confirmPassword
    }
}