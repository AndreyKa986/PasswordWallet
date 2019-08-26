package by.letum8658.passwordwallet.presenter

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import by.letum8658.passwordwallet.OnBackPressedListener
import by.letum8658.passwordwallet.R
import kotlinx.android.synthetic.main.fragment_create_item.*

class CreateItemFragment : Fragment(), CreateItemView, OnBackPressedListener {

    companion object {

        private const val ID_KEY = "id_key"

        fun getInstance(list: ArrayList<String>): CreateItemFragment {
            return CreateItemFragment().apply {
                arguments = Bundle().apply {
                    putStringArrayList(ID_KEY, list)
                }
            }
        }
    }

    private val presenter = CreateItemPresenter()
    private var listener: Listener? = null
    private val list by lazy { arguments?.getStringArrayList(ID_KEY) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_create_item, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        presenter.setView(this)

        presenter.setData(list)

        itemCreate.setOnClickListener {
            presenter.createPassword()
        }

        itemSave.setOnClickListener {
            presenter.saveItem()
        }
    }

    override fun setName(name: String) {
        itemName.setText(name)
    }

    override fun setPassword(password: String) {
        itemPassword.setText(password)
    }

    override fun setConfirmPassword(confirm: String) {
        itemConfirm.setText(confirm)
    }

    override fun getName(): String = itemName.text.toString()

    override fun getPassword(): String = itemPassword.text.toString()

    override fun getConfirmPassword(): String = itemConfirm.text.toString()

    override fun createPassword(name: String) {
        listener?.onCreatePasswordClick(name)
    }

    override fun saveItem() {
        listener?.onSaveItemClick()
    }

    override fun onBackPressed() {
        activity!!.supportFragmentManager
            .beginTransaction()
            .replace(R.id.container, RecyclerViewFragment())
            .commit()
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
        fun onCreatePasswordClick(name: String)
        fun onSaveItemClick()
    }
}