package by.letum8658.passwordwallet.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import by.letum8658.passwordwallet.Item
import by.letum8658.passwordwallet.ItemManager
import by.letum8658.passwordwallet.R
import kotlinx.android.synthetic.main.fragment_change_password.*

class ChangePasswordFragment : Fragment() {

    companion object {

        private const val ID_KEY = "id_key"

        fun getInstance(id: Long): CreateItemFragment {
            val fragment = CreateItemFragment()
            val bundle = Bundle()
            bundle.putLong(ID_KEY, id)
            fragment.arguments = bundle
            return fragment
        }
    }

    private var listener: Listener? = null
    private var id: Long = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        id = arguments?.getLong(ID_KEY, -1) ?: -1
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_change_password, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val item = ItemManager.getItemById(id.toInt())

        changeName.text = item!!.name

        changeSave.setOnClickListener {
            val password = changePassword.text.toString()
            val confirmP = changeConfirm.text.toString()
            if (password == confirmP) {
                val name = item.name
                ItemManager.updateItem(Item(name, password, id.toInt()))
                listener?.onSaveClick(id.toInt())
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
        fun onSaveClick(id: Int)
    }
}