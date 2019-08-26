package by.letum8658.passwordwallet.presenter

interface CreateItemView {

    fun setName(name: String)
    fun setPassword(password: String)
    fun setConfirmPassword(confirm: String)
    fun getName(): String
    fun getPassword(): String
    fun getConfirmPassword(): String
    fun createPassword(name: String)
    fun saveItem()
//    fun showMessage()
}