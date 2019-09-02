package by.letum8658.passwordwallet.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import by.letum8658.passwordwallet.R
import by.letum8658.passwordwallet.presenters.CreatePasswordPresenter
import by.letum8658.passwordwallet.view.views.CreatePasswordView
import kotlinx.android.synthetic.main.fragment_auto_create_password.*

class CreatePasswordFragment : Fragment(), CreatePasswordView {

    companion object {

        private const val ID_KEY = "id_key"
    }

    private val presenter = CreatePasswordPresenter()
    private val name by lazy { arguments!!.getString(ID_KEY, "Name") }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_auto_create_password, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val list = arrayListOf<String>()
                list.add(name)
                list.add("")
                val bundle = bundleOf(ID_KEY to list)
                view.findNavController()
                    .navigate(R.id.action_createPasswordFragment_callback, bundle)
            }
        }

        requireActivity().onBackPressedDispatcher.addCallback(this, callback)

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
        view?.let {
            val list = ArrayList<String>()
            list.add(name)
            list.add(autoPassword.text.toString())
            val bundle = bundleOf(ID_KEY to list)
            it.findNavController()
                .navigate(R.id.action_createPasswordFragment_to_createItemFragment, bundle)
        }
    }

    override fun onDetach() {
        super.onDetach()
        presenter.detach()
    }
}