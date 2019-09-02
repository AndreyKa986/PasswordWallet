package by.letum8658.passwordwallet.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import by.letum8658.passwordwallet.R
import by.letum8658.passwordwallet.presenters.DeleteItemPresenter
import by.letum8658.passwordwallet.view.views.DeleteItemView
import kotlinx.android.synthetic.main.fragment_delete_item.*

class DeleteItemFragment : Fragment(), DeleteItemView {

    companion object {

        private const val ID_KEY = "id_key"
    }

    private val presenter = DeleteItemPresenter()
    private lateinit var progressBar: ProgressBar
    private val list by lazy { arguments!!.getStringArrayList(ID_KEY) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_delete_item, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val bundle = bundleOf(ID_KEY to list)
                view.findNavController().navigate(R.id.action_deleteItemFragment_callback, bundle)
            }
        }

        requireActivity().onBackPressedDispatcher.addCallback(this, callback)

        progressBar = view.findViewById(R.id.delete_progress_circular)

        presenter.setView(this)

        deleteNo.setOnClickListener {
            val bundle = bundleOf(ID_KEY to list)
            view.findNavController()
                .navigate(R.id.action_deleteItemFragment_to_informationFragment, bundle)
        }

        deleteYes.setOnClickListener {
            presenter.yes(list!![0])
        }
    }

    override fun onYesClick() {
        view?.findNavController()?.navigate(R.id.action_deleteItemFragment_to_recyclerViewFragment)
    }

    override fun progressBarOn() {
        progressBar.visibility = View.VISIBLE
    }

    override fun progressBarOff() {
        progressBar.visibility = View.GONE
    }

    override fun showMessage() {
        Toast.makeText(context, R.string.error, Toast.LENGTH_SHORT).show()
    }

    override fun onDetach() {
        super.onDetach()
        presenter.detach()
    }
}