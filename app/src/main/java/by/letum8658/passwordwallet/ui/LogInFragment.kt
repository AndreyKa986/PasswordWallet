package by.letum8658.passwordwallet.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import by.letum8658.passwordwallet.AppPrefManager
import by.letum8658.passwordwallet.R
import kotlinx.android.synthetic.main.fragment_log_in.*

class LogInFragment : Fragment() {

    private var listener: Listener? = null
    private lateinit var prefsManager: AppPrefManager

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_log_in, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        prefsManager = AppPrefManager(context!!)
        logInUsername.setText(prefsManager.getName())

        logIn.setOnClickListener {
            listener?.onLogInClick(
                logInUsername.text.toString(),
                logInPassword.text.toString()
            )
        }

        logInCreate.setOnClickListener {
            listener?.onCreateClick()
        }
    }

    override fun onStop() {
        super.onStop()
        prefsManager.saveName(logInUsername.text.toString())
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
    }

    interface Listener {
        fun onLogInClick(username: String, password: String)
        fun onCreateClick()
    }
}