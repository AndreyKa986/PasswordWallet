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

        fun getInstance(item: String): InformationFragment {
            return InformationFragment().apply {
                arguments = Bundle().apply {
                    putString(ID_KEY, item)
                }
            }
        }

        fun getInstance(list: ArrayList<String>): InformationFragment {
            return InformationFragment().apply {
                arguments = Bundle().apply {
                    putStringArrayList(ID_KEY, list)
                }
            }
        }
    }

    private val presenter = InformationPresenter()
    private var listener: Listener? = null
    private val item by lazy { arguments!!.getString(ID_KEY, " ") }
    private val list by lazy { arguments!!.getStringArrayList(ID_KEY) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_item_information, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        presenter.setView(this)

        presenter.showData(item)

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

    override fun getInformationList(): ArrayList<String> = list

    override fun delete() {
        val password = informationPassword.text.toString()
        val list = arrayListOf(item, password)
        listener?.onDeleteClick(list)
    }

    override fun change() {
        val password = informationPassword.text.toString()
        val list = arrayListOf(item, password)
        listener?.onChangeClick(list)
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
        presenter.detach()
    }

    interface Listener {
        fun onDeleteClick(list: ArrayList<String>)
        fun onChangeClick(list: ArrayList<String>)
        fun onOkClick()
    }
}