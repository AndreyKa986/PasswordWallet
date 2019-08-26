package by.letum8658.passwordwallet.presenter

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import by.letum8658.passwordwallet.AppPrefManager
import by.letum8658.passwordwallet.R
import kotlinx.android.synthetic.main.fragment_log_in.*

class LogInFragment : Fragment(), LogInView {

    private val presenter = LogInPresenter()
    private var listener: Listener? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_log_in, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        presenter.setView(this)

        presenter.setName()

        logIn.setOnClickListener {
            presenter.logIn()
        }

        logInCreate.setOnClickListener {
            presenter.create()
        }
    }

    override fun getPrefsManager(): AppPrefManager = AppPrefManager(context!!)

    override fun setName(name: String) {
        logInUsername.setText(name)
    }

    override fun getName(): String = logInUsername.text.toString()

    override fun getPassword(): String = logInPassword.text.toString()

    override fun logIn() {
        listener?.onLogInClick()
    }

    override fun create() {
        listener?.onCreateAccountClick()
    }

    override fun onStop() {
        super.onStop()
        presenter.saveName()
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
        fun onLogInClick()
        fun onCreateAccountClick()
    }
}