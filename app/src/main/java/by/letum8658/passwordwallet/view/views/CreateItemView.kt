package by.letum8658.passwordwallet.view.views

interface CreateItemView {

    fun setName(name: String)
    fun setLogin(login: String)
    fun setPassword(password: String)
    fun setConfirmPassword(confirm: String)
    fun onSaveItemClick()
    fun showMessage(number: Int)
    fun progressBarOn()
    fun progressBarOff()
}