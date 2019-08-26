package by.letum8658.passwordwallet.presenter

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import by.letum8658.passwordwallet.R
import kotlinx.android.synthetic.main.fragment_delete_item.*

class DeleteItemFragment : Fragment(), DeleteItemView {

    companion object {

        private const val ID_KEY = "id_key"

        fun getInstance(list: ArrayList<String>): DeleteItemFragment {
            return DeleteItemFragment().apply {
                arguments = Bundle().apply {
                    putStringArrayList(ID_KEY, list)
                }
            }
        }
    }

    private val presenter = DeleteItemPresenter()
    private var listener: Listener? = null
    private val list by lazy { arguments!!.getStringArrayList(ID_KEY) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_delete_item, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        presenter.setView(this)

        deleteNo.setOnClickListener {
            presenter.no()
        }

        deleteYes.setOnClickListener {
            presenter.yes(list!![0])
        }
    }

    override fun no() {
        listener?.onNoClick(list)
    }

    override fun yes() {
        listener?.onYesClick()
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
        fun onNoClick(list: ArrayList<String>)
        fun onYesClick()
    }
}