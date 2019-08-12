package by.letum8658.passwordwallet.presenter

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import by.letum8658.passwordwallet.R
import kotlinx.android.synthetic.main.fragment_create_account.*

class CreateAccountFragment : Fragment() {

    private var listener: Listener? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_create_account, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        accountSave.setOnClickListener {
            if (isPasswordEqual()) {
                listener?.onSaveAccountClick(
                    accountUsername.text.toString(),
                    accountPassword.text.toString()
                )
            }
        }
    }

    private fun isPasswordEqual(): Boolean {
        val password = accountPassword.text.toString()
        val confirmPassword = accountConfirm.text.toString()
        return password == confirmPassword
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
        fun onSaveAccountClick(username: String, password: String)
    }
}