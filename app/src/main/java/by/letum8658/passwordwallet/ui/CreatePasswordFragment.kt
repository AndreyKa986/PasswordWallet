package by.letum8658.passwordwallet.ui

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment

class CreatePasswordFragment : Fragment() {

    companion object {

        private const val ID_KEY = "id_key"

        fun getInstance(id: Int): CreatePasswordFragment {
            return CreatePasswordFragment().apply {
                arguments = Bundle().apply {
                    putInt(ID_KEY, id)
                }
            }
        }
    }

    private var listener: Listener? = null
    private val id by lazy { arguments?.getInt(ID_KEY, -1) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
        fun onSaveClick()
    }
}