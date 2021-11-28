package com.sielom.idea_working.ui.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sielom.idea_working.R
import com.sielom.idea_working.adapter.IdeaAdapter
import com.sielom.idea_working.model.Idea
import com.sielom.idea_working.model.Status
import com.sielom.idea_working.viewmodel.PersonIdeasFragmentViewModel

private const val ARG_KEY_USER_ID = "user_id"

class PersonIdeasFragment : Fragment() {
    private val personIdeasFragmentViewModel: PersonIdeasFragmentViewModel by lazy {
        ViewModelProvider(this).get(PersonIdeasFragmentViewModel::class.java)
    }

    private var callbacks: Callbacks? = null

    private var ideaAdapter: IdeaAdapter? = null

    private lateinit var personProjectsButton: Button
    private lateinit var personDataButton: Button
    private lateinit var ideaRecyclerView: RecyclerView

    interface Callbacks {
        fun onPersonProjectsButtonFromPersonIdea()
        fun onPersonDataButtonButtonFromPersonIdea()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callbacks = context as Callbacks
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        ideaAdapter = IdeaAdapter(LayoutInflater.from(context))

        if (arguments?.containsKey(ARG_KEY_USER_ID) == true) {
            personIdeasFragmentViewModel.userId = arguments?.getInt(ARG_KEY_USER_ID) ?: -1
        }

        personIdeasFragmentViewModel.requestGetIdeas()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_person_ideas, container, false)

        personProjectsButton = view.findViewById(R.id.personProjectsButton)
        personDataButton = view.findViewById(R.id.personDataButton)
        ideaRecyclerView = view.findViewById(R.id.ideaRecyclerView)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ideaRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = ideaAdapter
        }

        personIdeasFragmentViewModel.ideasLiveData.observe(viewLifecycleOwner) { event ->
            when (event.status) {
                Status.LOADING -> loadingGetIdeas()
                Status.ERROR -> errorGetIdeas(event.error)
                Status.SUCCESS -> successGetIdeas(event.data)
            }
        }

        personProjectsButton.setOnClickListener {
            callbacks?.onPersonProjectsButtonFromPersonIdea()
        }

        personDataButton.setOnClickListener {
            callbacks?.onPersonDataButtonButtonFromPersonIdea()
        }
    }

    override fun onDetach() {
        super.onDetach()
        callbacks = null
    }

    private fun loadingGetIdeas() {

    }

    private fun errorGetIdeas(error: String?) {
        Toast.makeText(context, error, Toast.LENGTH_LONG).show()
    }

    private fun successGetIdeas(data: List<Idea>?) {
        if (data == null || data.isEmpty()) {
            Toast.makeText(context, "Идеи не найдены", Toast.LENGTH_SHORT).show()
            return
        }

        ideaAdapter?.submitList(data)
        ideaAdapter?.notifyDataSetChanged()
    }

    companion object {
        fun newInstance(userId: Int): PersonIdeasFragment {
            val args = Bundle()
            args.putInt(ARG_KEY_USER_ID, userId)

            val fragment = PersonIdeasFragment()
            fragment.arguments = args
            return fragment
        }
    }
}