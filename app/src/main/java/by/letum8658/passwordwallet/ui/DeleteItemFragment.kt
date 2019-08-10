package by.letum8658.passwordwallet.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import by.letum8658.passwordwallet.ItemManager
import by.letum8658.passwordwallet.R
import kotlinx.android.synthetic.main.fragment_delete_item.*

class DeleteItemFragment : Fragment() {

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
        return inflater.inflate(R.layout.fragment_delete_item, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        deleteNo.setOnClickListener {
            listener?.onNoClick(id)
        }

        deleteYes.setOnClickListener {
            val item = ItemManager.getItemById(id.toInt())
            ItemManager.deleteItem(item!!)
            listener?.onYesClick()
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
        fun onNoClick(id: Long)
        fun onYesClick()
    }
}