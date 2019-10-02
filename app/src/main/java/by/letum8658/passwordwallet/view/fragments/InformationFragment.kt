package by.letum8658.passwordwallet.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.View
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import by.letum8658.passwordwallet.R
import by.letum8658.passwordwallet.presenters.InformationPresenter
import by.letum8658.passwordwallet.view.views.InformationView
import kotlinx.android.synthetic.main.fragment_item_information.*

class InformationFragment : Fragment(), InformationView {

    companion object {

        private const val ID_KEY = "id_key"
        private const val INSTANCE_KEY = "instance_key"
    }

    private val presenter = InformationPresenter()
    private lateinit var progressBar: ProgressBar
    private lateinit var builder: AlertDialog.Builder
    private var actionBar: ActionBar? = null
    private val item by lazy { arguments!!.getString(ID_KEY, " ") }
    private val list by lazy { arguments?.getStringArrayList(ID_KEY) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_item_information, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        actionBar = (activity as AppCompatActivity).supportActionBar
        actionBar?.let {
            it.title = "    $item"
            it.show()
        }

        builder = AlertDialog.Builder(activity as AppCompatActivity)
        builder.setTitle(R.string.really)
            .setPositiveButton(R.string.yes) { _, _ -> presenter.deleteItem(item) }
            .setNegativeButton(R.string.no) { dialog, _ -> dialog.cancel() }
            .create()

        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                view.findNavController().navigate(R.id.action_informationFragment_callback)
            }
        }

        requireActivity().onBackPressedDispatcher.addCallback(this, callback)

        progressBar = view.findViewById(R.id.inform_progress_circular)

        presenter.setView(this)

        presenter.showData(item, savedInstanceState?.getString(INSTANCE_KEY), list)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        actionBar?.hide()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_info, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.change_info -> {
                change()
                true
            }
            R.id.delete_info -> {
                builder.show()
                true
            }
            android.R.id.home -> {
                back()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(INSTANCE_KEY, informationPassword?.text.toString())
    }

    override fun setName(name: String) {
        informationName.text = name
    }

    override fun setPassword(password: String) {
        informationPassword.text = password
    }

    override fun delete() {
        view?.apply { findNavController().navigate(R.id.action_informationFragment_to_recyclerViewFragment) }
    }

    override fun change() {
        view?.let {
            val password = informationPassword.text.toString()
            val itemName = informationName.text.toString()
            val list = arrayListOf(itemName, password)
            val bundle = bundleOf(ID_KEY to list)
            it.findNavController()
                .navigate(R.id.action_informationFragment_to_changePasswordFragment, bundle)
        }
    }

    private fun back() {
        view?.apply { findNavController().navigate(R.id.action_informationFragment_to_recyclerViewFragment) }
    }

    override fun showMessage() {
        Toast.makeText(context, R.string.error, Toast.LENGTH_SHORT).show()
    }

    override fun progressBarOn() {
        progressBar.visibility = View.VISIBLE
    }

    override fun progressBarOff() {
        progressBar.visibility = View.GONE
    }

    override fun onDetach() {
        super.onDetach()
        presenter.detach()
    }
}