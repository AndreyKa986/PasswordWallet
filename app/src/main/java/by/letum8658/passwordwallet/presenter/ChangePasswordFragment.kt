package by.letum8658.passwordwallet.presenter

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import by.letum8658.passwordwallet.BackPressed
import by.letum8658.passwordwallet.OnBackPressedListener
import by.letum8658.passwordwallet.R
import kotlinx.android.synthetic.main.fragment_change_password.*

class ChangePasswordFragment : Fragment(), ChangePasswordView, OnBackPressedListener {

    companion object {

        private const val ID_KEY = "id_key"

        fun getInstance(list: ArrayList<String>): ChangePasswordFragment {
            return ChangePasswordFragment().apply {
                arguments = Bundle().apply {
                    putStringArrayList(ID_KEY, list)
                }
            }
        }
    }

    private val presenter = ChangePasswordPresenter()
    private var listener: Listener? = null
    private val list by lazy { arguments!!.getStringArrayList(ID_KEY) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_change_password, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        presenter.setView(this)

        presenter.showItem(list!![0])

        changeSave.setOnClickListener {
            presenter.saveItem()
        }
    }

    override fun setName(name: String) {
        changeName.text = name
    }

    override fun getName(): String = changeName.text.toString()

    override fun getPassword(): String = changePassword.text.toString()

    override fun getConfirmPassword(): String = changeConfirm.text.toString()

    override fun saveChange(list: ArrayList<String>) {
        listener?.onSaveChangedClick(list)
    }

    override fun onBackPressed() {
        BackPressed.setList(list)
    }

    override fun showMessage() {
        Toast.makeText(context, R.string.password_not, Toast.LENGTH_SHORT).show()
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
        fun onSaveChangedClick(list: ArrayList<String>)
    }
}