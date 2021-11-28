package com.sielom.idea_working.ui.fragment

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
import com.sielom.idea_working.adapter.SpecialistAdapter
import com.sielom.idea_working.model.Category
import com.sielom.idea_working.model.Status
import com.sielom.idea_working.model.User
import com.sielom.idea_working.viewmodel.SpecialistsFragmentViewModel

private const val ARG_KEY_USER_ID = "user_id"

class SpecialistsFragment : Fragment() {

    private var specialistAdapter: SpecialistAdapter? = null

    private lateinit var userRecyclerView: RecyclerView
    private lateinit var categorySpinner: Spinner

    private var arrayAdapter: ArrayAdapter<Category>? = null

    private val specialistsFragmentViewModel: SpecialistsFragmentViewModel by lazy {
        ViewModelProvider(this).get(SpecialistsFragmentViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        specialistAdapter = SpecialistAdapter(LayoutInflater.from(context))
        context?.let {
            arrayAdapter = ArrayAdapter(it, android.R.layout.simple_spinner_item)
            arrayAdapter?.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }

        if (arguments?.containsKey(ARG_KEY_USER_ID) == true) {
            specialistsFragmentViewModel.userId = arguments?.getInt(ARG_KEY_USER_ID) ?: -1
        }

        specialistsFragmentViewModel.requestGetUsers(null)
        specialistsFragmentViewModel.requestGetCategories()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_specialists, container, false)

        userRecyclerView = view.findViewById(R.id.userRecyclerView)
        categorySpinner = view.findViewById(R.id.categorySpinner)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = specialistAdapter
        }

        categorySpinner.adapter = arrayAdapter
        categorySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val category = arrayAdapter?.getItem(position)
                specialistsFragmentViewModel.requestGetUsers(category)

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

        }

        specialistsFragmentViewModel.specialistsLiveData.observe(viewLifecycleOwner) { event ->
            when (event.status) {
                Status.LOADING -> loadingGetUsers()
                Status.ERROR -> errorGetUsers(event.error)
                Status.SUCCESS -> successGetUsers(event.data)
            }
        }

        specialistsFragmentViewModel.categoriesLiveData.observe(viewLifecycleOwner) { event ->
            when (event.status) {
                Status.LOADING -> loadingGetCategory()
                Status.ERROR -> errorGetCategory(event.error)
                Status.SUCCESS -> successGetCategory(event.data)
            }
        }
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

        arrayAdapter?.addAll(categories)

    }

    private fun loadingGetUsers() {

    }

    private fun errorGetUsers(error: String?) {
        Toast.makeText(context, error, Toast.LENGTH_LONG).show()
    }

    private fun successGetUsers(data: List<User>?) {
        if (data == null || data.isEmpty()) {
            Toast.makeText(context, "Пользователей нет", Toast.LENGTH_SHORT).show()
            return
        }

        specialistAdapter?.submitList(data)
        specialistAdapter?.notifyDataSetChanged()
    }

    companion object {
        fun newInstance(userId: Int): SpecialistsFragment {
            val args = Bundle()
            args.putInt(ARG_KEY_USER_ID, userId)

            val fragment = SpecialistsFragment()
            fragment.arguments = args
            return fragment
        }
    }
}