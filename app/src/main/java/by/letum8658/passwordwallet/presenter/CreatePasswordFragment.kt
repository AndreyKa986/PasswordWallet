package by.letum8658.passwordwallet.presenter

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import by.letum8658.passwordwallet.R
import kotlinx.android.synthetic.main.fragment_auto_create_password.*

class CreatePasswordFragment : Fragment() {

    private var listener: Listener? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_auto_create_password, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        autoCreate.setOnClickListener {
            autoPassword.setText(R.string.password)
        }

        autoSave.setOnClickListener {
            val password = autoPassword.text.toString()
            listener?.onSavePasswordClick(password)
        }
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
        fun onSavePasswordClick(password: String)
    }
}