package com.sielom.idea_working.ui.fragment

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sielom.idea_working.R
import com.sielom.idea_working.adapter.CategoryAdapter
import com.sielom.idea_working.model.Status
import com.sielom.idea_working.model.User
import com.sielom.idea_working.viewmodel.PersonDataFragmentViewModel

private const val ARG_KEY_USER_ID = "user_id"

class PersonDataFragment : Fragment() {

    private val personDataFragmentViewModel: PersonDataFragmentViewModel by lazy {
        ViewModelProvider(this).get(PersonDataFragmentViewModel::class.java)
    }

    private var callbacks: Callbacks? = null

    private var categoryAdapter: CategoryAdapter? = null

    private lateinit var personProjectsButton: Button
    private lateinit var personIdeasButton: Button
    private lateinit var nameTextView: TextView
    private lateinit var countProjectTextView: TextView
    private lateinit var addCategoryImageView: ImageView
    private lateinit var aboutPersonEditText: EditText
    private lateinit var saveUserInformationButton: Button
    private lateinit var categoryRecyclerView: RecyclerView
    private lateinit var logOutButton: Button

    interface Callbacks {
        fun onPersonProjectsButtonClickFromPersonData()
        fun onPersonIdeasButtonClickFromPersonData()
        fun onLogOutButtonClick()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callbacks = context as Callbacks
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        categoryAdapter = CategoryAdapter(LayoutInflater.from(context))

        if (arguments?.containsKey(ARG_KEY_USER_ID) == true) {
            personDataFragmentViewModel.userId = arguments?.getInt(ARG_KEY_USER_ID) ?: -1
        }

        personDataFragmentViewModel.requestGetUserInformation()
        personDataFragmentViewModel.requestGetCountProjects()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_person_data, container, false)

        personProjectsButton = view.findViewById(R.id.personProjectsButton)
        nameTextView = view.findViewById(R.id.nameTextView)
        countProjectTextView = view.findViewById(R.id.countProjectTextView)
        addCategoryImageView = view.findViewById(R.id.addDirectionImageView)
        personIdeasButton = view.findViewById(R.id.personIdeasButton)
        aboutPersonEditText = view.findViewById(R.id.aboutPersonEditText)
        categoryRecyclerView = view.findViewById(R.id.categoryRecyclerView)
        saveUserInformationButton = view.findViewById(R.id.saveUserInformationButton)
        logOutButton = view.findViewById(R.id.logOutButton)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        personIdeasButton.setOnClickListener {
            callbacks?.onPersonIdeasButtonClickFromPersonData()
        }

        personProjectsButton.setOnClickListener {
            callbacks?.onPersonProjectsButtonClickFromPersonData()
        }

        addCategoryImageView.setOnClickListener {
            val dialogFragment = AllCategoriesFragment()
            activity?.supportFragmentManager?.let {
                dialogFragment.show(it, "AllCategoriesFragment")
            }
        }

        logOutButton.setOnClickListener {
            callbacks?.onLogOutButtonClick()
        }

        saveUserInformationButton.setOnClickListener {
            personDataFragmentViewModel.requestSaveUserInformation()
        }

        aboutPersonEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                s?.let {
                    personDataFragmentViewModel.about = s.toString()
                }
            }

            override fun afterTextChanged(s: Editable?) {
            }

        })

        personDataFragmentViewModel.userLiveData.observe(viewLifecycleOwner) { event ->
            when (event.status) {
                Status.LOADING -> loadingGetUser()
                Status.ERROR -> errorGetUser(event.error)
                Status.SUCCESS -> successGetUser(event.data)
            }
        }

        personDataFragmentViewModel.countProjectLiveData.observe(viewLifecycleOwner) { event ->
            when (event.status) {
                Status.LOADING -> loadingGetCountProject()
                Status.ERROR -> errorGetCountProject(event.error)
                Status.SUCCESS -> successGetCountProject(event.data)
            }
        }

        personDataFragmentViewModel.saveUserLiveData.observe(viewLifecycleOwner) { event ->
            when (event.status) {
                Status.LOADING -> loadingSaveUser()
                Status.ERROR -> errorSaveUser(event.error)
                Status.SUCCESS -> successSaveUser(event.data)
            }
        }

        categoryRecyclerView.apply {
            layoutManager = LinearLayoutManager(
                context,
                LinearLayoutManager.HORIZONTAL,
                false
            )

            adapter = categoryAdapter
        }
    }

    private fun loadingSaveUser() {
    }

    private fun errorSaveUser(error: String?) {
        Toast.makeText(context, error, Toast.LENGTH_LONG).show()
    }

    private fun successSaveUser(data: String?) {
        Toast.makeText(context, "Данные успешно сохранены", Toast.LENGTH_SHORT).show()
    }

    private fun loadingGetCountProject() {

    }

    private fun errorGetCountProject(error: String?) {
        Toast.makeText(context, error, Toast.LENGTH_LONG).show()
    }

    private fun successGetCountProject(data: Int?) {
        countProjectTextView.text = "Проектов: " + (data ?: 0).toString()
    }

    override fun onDetach() {
        super.onDetach()
        callbacks = null
    }

    private fun loadingGetUser() {

    }

    private fun errorGetUser(error: String?) {
        Toast.makeText(context, error, Toast.LENGTH_LONG).show()
    }

    private fun successGetUser(data: User?) {
        data?.let { user ->
            nameTextView.text = user.username
            aboutPersonEditText.hint = user.about ?: "Ифнормация обо мне"

            data.categories?.let { categories ->
                val categoryIdList: MutableList<Int> = mutableListOf()
                for (category in categories) {
                    categoryIdList.add(category.id)
                }

                personDataFragmentViewModel.categoryIdList.clear()
                personDataFragmentViewModel.categoryIdList.addAll(categoryIdList)
            }

            categoryAdapter?.submitList(data.categories)
            categoryAdapter?.notifyDataSetChanged()
        }
    }

    companion object {
        fun newInstance(userId: Int): PersonDataFragment {
            val args = Bundle()
            args.putInt(ARG_KEY_USER_ID, userId)

            val fragment = PersonDataFragment()
            fragment.arguments = args
            return fragment
        }
    }
}