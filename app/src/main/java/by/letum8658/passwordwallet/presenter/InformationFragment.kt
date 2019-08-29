package by.letum8658.passwordwallet.presenter

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import by.letum8658.passwordwallet.R
import kotlinx.android.synthetic.main.fragment_item_information.*

class InformationFragment : Fragment(), InformationView {

    companion object {

        private const val ID_KEY = "id_key"

        fun getInstance(id: Int): InformationFragment {
            return InformationFragment().apply {
                arguments = Bundle().apply {
                    putInt(ID_KEY, id)
                }
            }
        }
    }

    private val presenter = InformationPresenter()
    private var listener: Listener? = null
    private val id by lazy { arguments?.getInt(ID_KEY, -1) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_item_information, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        presenter.setView(this)

        presenter.showData(id!!)

        informationDelete.setOnClickListener {
            presenter.delete()
        }

        informationChange.setOnClickListener {
            presenter.change()
        }

        informationOK.setOnClickListener {
            presenter.ok()
        }
    }

    override fun setName(name: String) {
        informationName.text = name
    }

    override fun setPassword(password: String) {
        informationPassword.text = password
    }

    override fun delete() {
        listener?.onDeleteClick(id!!)
    }

    override fun change() {
        listener?.onChangeClick(id!!)
    }

    override fun ok() {
        listener?.onOkClick()
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
        fun onDeleteClick(id: Int)
        fun onChangeClick(id: Int)
        fun onOkClick()
    }
}