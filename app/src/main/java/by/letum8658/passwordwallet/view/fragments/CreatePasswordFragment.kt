package by.letum8658.passwordwallet.view.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import by.letum8658.passwordwallet.model.OnBackPressedListener
import by.letum8658.passwordwallet.R
import by.letum8658.passwordwallet.model.EntityManager
import by.letum8658.passwordwallet.presenters.CreatePasswordPresenter
import by.letum8658.passwordwallet.view.views.CreatePasswordView
import kotlinx.android.synthetic.main.fragment_auto_create_password.*

class CreatePasswordFragment : Fragment(),
    CreatePasswordView, OnBackPressedListener {

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
            savePassword()
        }
    }

    override fun setPassword(password: String) {
        autoPassword.setText(password)
    }

    private fun savePassword() {
        val list = ArrayList<String>()
        list.add(name)
        list.add(autoPassword.text.toString())
        listener?.onSavePasswordClick(list)
    }

    override fun onBackPressed() {
        val list = arrayListOf<String>()
        list.add(name)
        list.add("")
        EntityManager.setList(list)
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