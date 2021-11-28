package com.sielom.idea_working.ui.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sielom.idea_working.R
import com.sielom.idea_working.adapter.ProjectAdapter
import com.sielom.idea_working.adapter.SpecialistAdapter
import com.sielom.idea_working.model.Category
import com.sielom.idea_working.model.Project
import com.sielom.idea_working.model.Status
import com.sielom.idea_working.viewmodel.ProjectsFragmentViewModel

private const val ARG_KEY_USER_ID = "user_id"

class ProjectsFragment : Fragment() {

    private val projectsFragmentViewModel: ProjectsFragmentViewModel by lazy {
        ViewModelProvider(this).get(ProjectsFragmentViewModel::class.java)
    }

    private var callbacks: Callbacks? = null

    private var projectAdapter: ProjectAdapter? = null
    private var categoryAdapter: ArrayAdapter<Category>? = null

    private lateinit var projectsRecyclerView: RecyclerView
    private lateinit var categorySpinner: Spinner

    interface Callbacks {
        fun onEnterProjectButtonClickFromProjectsFragment(projectId: Int)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callbacks = context as Callbacks
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (arguments?.containsKey(ARG_KEY_USER_ID) == true) {
            projectsFragmentViewModel.userId = arguments?.getInt(ARG_KEY_USER_ID) ?: -1
        }

        projectsFragmentViewModel.requestGetProjects(null)
        projectsFragmentViewModel.requestGetCategories()

        projectAdapter = ProjectAdapter(LayoutInflater.from(context))
        projectAdapter?.setOnClickListener(object: ProjectAdapter.ProjectOnCLickListener {
            override fun onEnterProjectButtonClick(projectId: Int) {
                callbacks?.onEnterProjectButtonClickFromProjectsFragment(projectId)
            }

        })

        context?.let {
            categoryAdapter = ArrayAdapter(it, android.R.layout.simple_spinner_item)
            categoryAdapter?.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_projects, container, false)

        projectsRecyclerView = view.findViewById(R.id.projectsRecyclerView)
        categorySpinner = view.findViewById(R.id.categorySpinner)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        projectsRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = projectAdapter
        }

        categorySpinner.adapter = categoryAdapter
        categorySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val category = categoryAdapter?.getItem(position)
                projectsFragmentViewModel.requestGetProjects(category)

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

        }

        projectsFragmentViewModel.projectsLiveData.observe(viewLifecycleOwner) { event ->
            when (event.status) {
                Status.LOADING -> loadingGetProjects()
                Status.ERROR -> errorGetProjects(event.error)
                Status.SUCCESS -> successGetProjects(event.data)
            }
        }

        projectsFragmentViewModel.categoriesLiveData.observe(viewLifecycleOwner) { event ->
            when (event.status) {
                Status.LOADING -> loadingGetCategory()
                Status.ERROR -> errorGetCategory(event.error)
                Status.SUCCESS -> successGetCategory(event.data)
            }
        }
    }

    override fun onDetach() {
        super.onDetach()
        callbacks = null
    }

    private fun loadingGetCategory() {

    }

    private fun errorGetCategory(error: String?) {
        Toast.makeText(context, error, Toast.LENGTH_LONG).show()
    }

    private fun successGetCategory(data: List<Category>?) {
        if (data == null || data.isEmpty()) {
            return
        }

        val categories: MutableList<Category> = mutableListOf()
        categories.add(Category(-1, "Все"))
        categories.addAll(data)

        categoryAdapter?.addAll(categories)
    }

    private fun loadingGetProjects() {
    }

    private fun errorGetProjects(error: String?) {
        Toast.makeText(context, error, Toast.LENGTH_LONG).show()
    }

    private fun successGetProjects(data: List<Project>?) {
        if (data == null || data.isEmpty()) {
            Toast.makeText(context, "Нет проектов", Toast.LENGTH_SHORT).show()
            return
        }

        projectAdapter?.submitList(data)
        projectAdapter?.notifyDataSetChanged()
    }

    companion object {
        fun newInstance(userId: Int): ProjectsFragment {
            val args = Bundle()
            args.putInt(ARG_KEY_USER_ID, userId)

            val fragment = ProjectsFragment()
            fragment.arguments = args
            return fragment
        }
    }
}