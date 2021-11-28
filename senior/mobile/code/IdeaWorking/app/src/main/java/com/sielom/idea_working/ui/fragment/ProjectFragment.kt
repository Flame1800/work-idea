package com.sielom.idea_working.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sielom.idea_working.R
import com.sielom.idea_working.adapter.SpecialistAdapter
import com.sielom.idea_working.model.Category
import com.sielom.idea_working.model.Project
import com.sielom.idea_working.model.Status
import com.sielom.idea_working.model.User
import com.sielom.idea_working.viewmodel.ProjectFragmentViewModel

private const val ARG_KEY_USER_ID = "user_id"
private const val ARG_KEY_PROJECT_ID = "project_id"

class ProjectFragment : Fragment() {

    private val projectFragmentViewModel: ProjectFragmentViewModel by lazy {
        ViewModelProvider(this).get(ProjectFragmentViewModel::class.java)
    }

    private var specialistAdapter: SpecialistAdapter? = null

    private lateinit var nameTextView: TextView
    private lateinit var categoriesTextView: TextView
    private lateinit var titleProjectTextView: TextView
    private lateinit var descriptionProjectTextView: TextView
    private lateinit var enterProjectButton: Button
    private lateinit var participantRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (arguments?.containsKey(ARG_KEY_USER_ID) == true) {
            projectFragmentViewModel.userId = arguments?.getInt(ARG_KEY_USER_ID) ?: -1
        }

        if (arguments?.containsKey(ARG_KEY_PROJECT_ID) == true) {
            projectFragmentViewModel.projectId = arguments?.getInt(ARG_KEY_PROJECT_ID) ?: -1
        }

        projectFragmentViewModel.requestGetProject()

        specialistAdapter = SpecialistAdapter(LayoutInflater.from(context))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_project, container, false)

        nameTextView = view.findViewById(R.id.nameTextView)
        categoriesTextView = view.findViewById(R.id.categoriesTextView)
        titleProjectTextView = view.findViewById(R.id.titleProjectTextView)
        descriptionProjectTextView = view.findViewById(R.id.descriptionProjectTextView)
        enterProjectButton = view.findViewById(R.id.enterProjectButton)
        participantRecyclerView = view.findViewById(R.id.participantRecyclerView)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        participantRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = specialistAdapter
        }

        projectFragmentViewModel.projectLiveData.observe(viewLifecycleOwner) { event ->
            when (event.status) {
                Status.LOADING -> loadingGetProject()
                Status.ERROR -> errorGetProject(event.error)
                Status.SUCCESS -> successGetProject(event.data)
            }
        }

        projectFragmentViewModel.specializationsLiveData.observe(viewLifecycleOwner) { event ->
            when (event.status) {
                Status.LOADING -> loadingGetSpecializations()
                Status.ERROR -> errorGetSpecializations(event.error)
                Status.SUCCESS -> successGetSpecializations(event.data)
            }
        }

        projectFragmentViewModel.specialistsLiveData.observe(viewLifecycleOwner) { event ->
            when (event.status) {
                Status.LOADING -> loadingGetSpecialists()
                Status.ERROR -> errorGetSpecialists(event.error)
                Status.SUCCESS -> successGetSpecialists(event.data)
            }
        }
    }

    private fun loadingGetSpecialists() {

    }

    private fun errorGetSpecialists(error: String?) {
        Toast.makeText(context, error, Toast.LENGTH_LONG).show()
    }

    private fun successGetSpecialists(data: List<User>?) {
        if (data == null || data.isEmpty()) {
            return
        }

        specialistAdapter?.submitList(data)
        specialistAdapter?.notifyDataSetChanged()
    }

    private fun loadingGetSpecializations() {
    }

    private fun errorGetSpecializations(error: String?) {
//        Toast.makeText(context, error, Toast.LENGTH_LONG).show()
    }

    private fun successGetSpecializations(data: List<Category>?) {
        if (data == null || data.isEmpty()) {
//            Toast.makeText(context, "Для проекта не указаны категории", Toast.LENGTH_LONG).show()
            return
        }

        var specializations: String = ""
        for (specialization in data) {
            specializations += specialization.title + "; "
        }

        categoriesTextView.text = specializations
    }

    private fun loadingGetProject() {
    }

    private fun errorGetProject(error: String?) {
        Toast.makeText(context, error, Toast.LENGTH_LONG).show()
    }

    private fun successGetProject(data: Project?) {
        if (data == null) {
            Toast.makeText(context, "Проекта не существует", Toast.LENGTH_SHORT).show()
            return
        }

        nameTextView.text = data.author?.username ?: "Неизвестно"
        titleProjectTextView.text = data.title
        descriptionProjectTextView.text = data.description

        val specializationIdList: MutableList<Int> = mutableListOf()
        for (line in data.requiredSpecializations) {
            specializationIdList.add(line["specialization"]?.toInt() ?: -1)
        }

        projectFragmentViewModel.requestGetSpecialization(specializationIdList)
        data.participants?.let {
            projectFragmentViewModel.requestGetUsers(it)
        }
    }

    companion object {
        fun newInstance(userId: Int, projectId: Int): ProjectFragment {
            val args = Bundle()
            args.putInt(ARG_KEY_USER_ID, userId)
            args.putInt(ARG_KEY_PROJECT_ID, projectId)

            val fragment = ProjectFragment()
            fragment.arguments = args
            return fragment
        }
    }


}