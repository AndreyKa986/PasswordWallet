package by.letum8658.passwordwallet.presenter

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import by.letum8658.passwordwallet.Item
import by.letum8658.passwordwallet.ItemManager
import by.letum8658.passwordwallet.R
import kotlinx.android.synthetic.main.fragment_create_item.*

class CreateItemFragment : Fragment() {

    companion object {

        private const val ID_KEY = "id_key"

        fun getInstance(password: String): CreateItemFragment {
            return CreateItemFragment().apply {
                arguments = Bundle().apply {
                    putString(ID_KEY, password)
                }
            }
        }
    }

    private var listener: Listener? = null
    private val password by lazy { arguments?.getString(ID_KEY, "defaultString") }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_create_item, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        if (password != null || password != "defaultString") {
            itemPassword.setText(password)
            itemConfirm.setText(password)
        }

        itemCreate.setOnClickListener {
            listener?.onCreatePasswordClick()
        }

        itemSave.setOnClickListener {
            val name = itemName.text.toString()
            val password = itemPassword.text.toString()
            val confirmP = itemConfirm.text.toString()
            if (password == confirmP) {
                val id = System.currentTimeMillis().toInt()
                ItemManager.addNewItem(Item(name, password, id))
                listener?.onSaveItemClick()
            }
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
        fun onCreatePasswordClick()
        fun onSaveItemClick()
    }
}