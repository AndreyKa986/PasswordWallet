package by.letum8658.passwordwallet.presenter

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import by.letum8658.passwordwallet.BackPressed
import by.letum8658.passwordwallet.OnBackPressedListener
import by.letum8658.passwordwallet.R
import kotlinx.android.synthetic.main.fragment_auto_create_password.*

class CreatePasswordFragment : Fragment(), CreatePasswordView, OnBackPressedListener {

    companion object {

        private const val ID_KEY = "id_key"

        fun getInstance(name: String): CreatePasswordFragment {
            return CreatePasswordFragment().apply {
                arguments = Bundle().apply {
                    putString(ID_KEY, name)
                }
            }
        }
    }

    private val presenter = CreatePasswordPresenter()
    private var listener: Listener? = null
    private val name by lazy { arguments!!.getString(ID_KEY, "Name") }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_auto_create_password, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        presenter.setView(this)

        autoCreate.setOnClickListener {
            presenter.createPassword()
        }

        autoSave.setOnClickListener {
            presenter.savePassword(name)
        }
    }

    override fun setPassword(password: String) {
        autoPassword.setText(password)
    }

    override fun getPassword(): String = autoPassword.text.toString()

    override fun savePassword(list: ArrayList<String>) {
        listener?.onSavePasswordClick(list)
    }

    override fun onBackPressed() {
        val list = ArrayList<String>()
        list.add(name)
        list.add("")
        BackPressed.setList(list)
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
        fun onSavePasswordClick(list: ArrayList<String>)
    }
}