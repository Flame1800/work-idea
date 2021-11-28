package com.sielom.idea_working.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sielom.idea_working.R
import com.sielom.idea_working.adapter.IdeaAdapter
import com.sielom.idea_working.model.Idea
import com.sielom.idea_working.model.Status
import com.sielom.idea_working.viewmodel.IdeasFragmentViewModel

private const val ARG_KEY_USER_ID = "user_id"

class IdeasFragment : Fragment() {

    private val ideasFragmentViewModel: IdeasFragmentViewModel by lazy {
        ViewModelProvider(this).get(IdeasFragmentViewModel::class.java)
    }

    private var ideaAdapter: IdeaAdapter? = null

    private lateinit var ideaRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ideaAdapter = IdeaAdapter(LayoutInflater.from(context))

        if (arguments?.containsKey(ARG_KEY_USER_ID) == true) {
            ideasFragmentViewModel.userId = arguments?.getInt(ARG_KEY_USER_ID) ?: -1
        }

        ideasFragmentViewModel.requestGetIdeas()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_ideas, container, false)

        ideaRecyclerView = view.findViewById(R.id.ideaRecyclerView)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ideaRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = ideaAdapter
        }

        ideasFragmentViewModel.ideasLiveData.observe(viewLifecycleOwner) { event ->
            when (event.status) {
                Status.LOADING -> loadingGetIdeas()
                Status.ERROR -> errorGetIdeas(event.error)
                Status.SUCCESS -> successGetIdeas(event.data)
            }
        }
    }

    private fun loadingGetIdeas() {

    }

    private fun errorGetIdeas(error: String?) {
        Toast.makeText(context, error, Toast.LENGTH_LONG).show()
    }

    private fun successGetIdeas(data: List<Idea>?) {
        if (data == null || data.isEmpty()) {
            Toast.makeText(context, "Нет идей", Toast.LENGTH_SHORT).show()
            return
        }

        ideaAdapter?.submitList(data)
        ideaAdapter?.notifyDataSetChanged()
    }

    companion object {
        fun newInstance(userId: Int): IdeasFragment {
            val args = Bundle()
            args.putInt(ARG_KEY_USER_ID, userId)

            val fragment = IdeasFragment()
            fragment.arguments = args
            return fragment
        }
    }
}