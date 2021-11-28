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
import com.sielom.idea_working.viewmodel.ExampleWorkFragmentViewModel

class ExampleWorkFragment : Fragment() {
    private val exampleWorkFragmentViewModel: ExampleWorkFragmentViewModel by lazy {
        ViewModelProvider(this).get(ExampleWorkFragmentViewModel::class.java)
    }

    private var callbacks: Callbacks? = null

    private var projectAdapter: ProjectAdapter? = null

    private lateinit var goToEndSliderButton: Button
    private lateinit var projectsRecyclerView: RecyclerView

    interface Callbacks {
        fun onGoToEndSliderButtonClick()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callbacks = context as Callbacks
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        projectAdapter = ProjectAdapter(LayoutInflater.from(context))
        exampleWorkFragmentViewModel.requestGetProjects()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_example_work, container, false)

        goToEndSliderButton = view.findViewById(R.id.goToEndSliderButton)
        projectsRecyclerView = view.findViewById(R.id.projectsRecyclerView)


        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        projectsRecyclerView.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = projectAdapter
        }

        goToEndSliderButton.setOnClickListener {
            callbacks?.onGoToEndSliderButtonClick()
        }

        exampleWorkFragmentViewModel.projectsLiveData.observe(viewLifecycleOwner) { event ->
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
        Toast.makeText(context, error, Toast.LENGTH_LONG)
    }

    private fun successGetProjects(data: List<Project>?) {
        projectAdapter?.submitList(data)
        projectAdapter?.notifyDataSetChanged()
    }

    override fun onDetach() {
        super.onDetach()
        callbacks = null
    }
}