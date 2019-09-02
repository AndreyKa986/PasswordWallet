package by.letum8658.passwordwallet.view.fragments

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.activity.OnBackPressedCallback
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.letum8658.passwordwallet.R
import by.letum8658.passwordwallet.adapters.Adapter
import by.letum8658.passwordwallet.presenters.RecyclerViewPresenter
import by.letum8658.passwordwallet.view.views.RecyclerViewView
import kotlinx.android.synthetic.main.fragment_recyclerview_items.*

class RecyclerViewFragment : Fragment(), RecyclerViewView, Adapter.ClickListener {

    companion object {

        private const val ID_KEY = "id_key"
    }

    private val presenter = RecyclerViewPresenter()
    private lateinit var adapter: Adapter
    private lateinit var progressBar: ProgressBar

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_recyclerview_items, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                view.findNavController().navigate(R.id.action_recyclerViewFragment_callback)
            }
        }

        requireActivity().onBackPressedDispatcher.addCallback(this, callback)

        progressBar = view.findViewById(R.id.recycler_progress_circular)

        presenter.setView(this)

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(context)

        val database = presenter.getDataBase()
        adapter = Adapter(database, this)
        recyclerView.adapter = adapter

        search.addTextChangedListener(object : TextWatcher {

            private val handler = Handler(Looper.getMainLooper())
            private lateinit var workRunnable: Runnable

            override fun afterTextChanged(string: Editable?) {
                workRunnable = Runnable { updateList() }
                handler.removeCallbacks(workRunnable)
                handler.postDelayed(workRunnable, 300L)
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        floatActionButton.setOnClickListener {
            view.findNavController()
                .navigate(R.id.action_recyclerViewFragment_to_createItemFragment)
        }
    }

    override fun onItemClick(item: String) {
        view?.let {
            val bundle = bundleOf(ID_KEY to item)
            it.findNavController()
                .navigate(R.id.action_recyclerViewFragment_to_informationFragment, bundle)
        }
    }

    private fun updateList() {
        val text = search.text.toString()
        adapter.itemListBySearch(presenter.getSearchList(text))
    }

    override fun updateDatabase() {
        updateList()
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