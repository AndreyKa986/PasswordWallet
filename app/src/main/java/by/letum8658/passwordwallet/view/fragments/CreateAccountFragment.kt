package by.letum8658.passwordwallet.view.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import by.letum8658.passwordwallet.model.OnBackPressedListener
import by.letum8658.passwordwallet.R
import by.letum8658.passwordwallet.presenters.CreateAccountPresenter
import by.letum8658.passwordwallet.view.views.CreateAccountView
import kotlinx.android.synthetic.main.fragment_create_account.*

class CreateAccountFragment : Fragment(), CreateAccountView,
    OnBackPressedListener {

    private val presenter = CreateAccountPresenter()
    private var listener: Listener? = null
    private lateinit var progressBar: ProgressBar

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_create_account, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        progressBar = view.findViewById(R.id.account_progress_circular)

        presenter.setView(this)

        accountSave.setOnClickListener {
            presenter.createAccount()
        }
    }

    override fun getName(): String = accountUsername.text.toString()

    override fun getPassword(): String = accountPassword.text.toString()

    override fun getConfirmPassword(): String = accountConfirm.text.toString()

    override fun createAccount() {
        listener?.onSaveAccountClick()
    }

    override fun onBackPressed() {}

    override fun showMessage(number: Int) {
        when (number) {
            1 -> Toast.makeText(context, R.string.have_user, Toast.LENGTH_SHORT).show()
            2 -> Toast.makeText(context, R.string.password_not, Toast.LENGTH_SHORT).show()
            3 -> Toast.makeText(context, R.string.take_name, Toast.LENGTH_SHORT).show()
            4 -> Toast.makeText(context, R.string.error, Toast.LENGTH_SHORT).show()
        }
    }

    override fun progressBarOn() {
        progressBar.visibility = View.VISIBLE
    }

    override fun progressBarOff() {
        progressBar.visibility = View.GONE
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is Listener) {
            listener = context
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
        presenter.detach()
    }

    interface Listener {
        fun onSaveAccountClick()
    }
}