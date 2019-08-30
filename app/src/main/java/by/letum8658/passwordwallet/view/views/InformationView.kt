package by.letum8658.passwordwallet.view.views

interface InformationView {

    fun setName(name: String)
    fun setPassword(password: String)
    fun delete()
    fun change()
    fun progressBarOn()
    fun progressBarOff()
    fun showMessage()
}