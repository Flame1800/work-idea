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
import com.sielom.idea_working.adapter.ProjectAdapter
import com.sielom.idea_working.model.Project
import com.sielom.idea_working.model.Status
import com.sielom.idea_working.viewmodel.PersonProjectsFragmentViewModel

private const val ARG_KEY_USER_ID = "user_id"

class PersonProjectsFragment : Fragment() {
    private val personProjectsFragmentViewModel: PersonProjectsFragmentViewModel by lazy {
        ViewModelProvider(this).get(PersonProjectsFragmentViewModel::class.java)
    }

    private var callbacks: Callbacks? = null

    private var projectAdapter: ProjectAdapter? = null

    private lateinit var projectsRecyclerView: RecyclerView
    private lateinit var personIdeasButton: Button
    private lateinit var personDataButton: Button


    interface Callbacks {
        fun onPersonDataButtonClick()
        fun onPersonIdeasButtonClick()
        fun onEnterProjectButtonClickFromPersonProjectFragment(projectId: Int)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callbacks = context as Callbacks
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        projectAdapter = ProjectAdapter(LayoutInflater.from(context))
        projectAdapter?.setOnClickListener(object : ProjectAdapter.ProjectOnCLickListener {
            override fun onEnterProjectButtonClick(projectId: Int) {
                callbacks?.onEnterProjectButtonClickFromPersonProjectFragment(projectId)
            }

        })

        if (arguments?.containsKey(ARG_KEY_USER_ID) == true) {
            val userId = arguments?.getInt(ARG_KEY_USER_ID) ?: -1

            personProjectsFragmentViewModel.userId = userId
            personProjectsFragmentViewModel.requestGetProjects()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_person_projects, container, false)

        projectsRecyclerView = view.findViewById(R.id.projectsRecyclerView)
        personIdeasButton = view.findViewById(R.id.personIdeasButton)
        personDataButton = view.findViewById(R.id.personDataButton)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        projectsRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = projectAdapter
        }

        personIdeasButton.setOnClickListener {
            callbacks?.onPersonIdeasButtonClick()
        }

        personDataButton.setOnClickListener {
            callbacks?.onPersonDataButtonClick()
        }

        personProjectsFragmentViewModel.projectsLiveData.observe(viewLifecycleOwner) { event ->
            when (event.status) {
                Status.LOADING -> loadingGetProjects()
                Status.ERROR -> errorGetProjects(event.error)
                Status.SUCCESS -> successGetProjects(event.data)
            }
        }
    }

    private fun loadingGetProjects() {
    }

    private fun errorGetProjects(error: String?) {
        Toast.makeText(context, error, Toast.LENGTH_LONG).show()
    }

    private fun successGetProjects(data: List<Project>?) {
        if (data == null) {
            Toast.makeText(context, "У вас нет проектов", Toast.LENGTH_SHORT).show()
            return
        }

        projectAdapter?.submitList(data)
        projectAdapter?.notifyDataSetChanged()
    }

    override fun onDetach() {
        super.onDetach()
        callbacks = null
    }

    companion object {
        fun newInstance(userId: Int): PersonProjectsFragment {
            val args = Bundle()
            args.putInt(ARG_KEY_USER_ID, userId)

            val fragment = PersonProjectsFragment()
            fragment.arguments = args
            return fragment
        }
    }
}