package com.sielom.idea_working.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sielom.idea_working.R
import com.sielom.idea_working.adapter.AllCategoryAdapter
import com.sielom.idea_working.model.Category
import com.sielom.idea_working.model.Status
import com.sielom.idea_working.viewmodel.AllCategoriesFragmentViewModel

private const val ARG_KEY_CATEGORY_ID_LIST = "category_id_list"

class AllCategoriesFragment : DialogFragment() {


    private val allCategoriesFragmentViewModel: AllCategoriesFragmentViewModel by lazy {
        ViewModelProvider(this).get(AllCategoriesFragmentViewModel::class.java)
    }

    private var allCategoryAdapter: AllCategoryAdapter? = null

    private lateinit var categoryRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        allCategoryAdapter = AllCategoryAdapter(LayoutInflater.from(context))

        if (arguments?.containsKey(ARG_KEY_CATEGORY_ID_LIST) == true) {
            val list: MutableList<Int> = mutableListOf()
            arguments?.let {
                list.addAll(it.getIntArray(ARG_KEY_CATEGORY_ID_LIST) as Collection<Int>)
            }
        }

        allCategoriesFragmentViewModel.requestGetCategories()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_all_categories, container)

        categoryRecyclerView = view.findViewById(R.id.categoryRecyclerView)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        categoryRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = allCategoryAdapter
        }

        allCategoriesFragmentViewModel.categoriesLiveData.observe(viewLifecycleOwner) { event ->
            when (event.status) {
                Status.LOADING -> loadingGetCategories()
                Status.ERROR -> errorGetCategories(event.error)
                Status.SUCCESS -> successGetCategories(event.data)
            }
        }

        dialog?.window?.setSoftInputMode(
            WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE
        )
    }

    private fun loadingGetCategories() {

    }

    private fun errorGetCategories(error: String?) {
        Toast.makeText(context, error, Toast.LENGTH_LONG).show()
    }

    private fun successGetCategories(data: List<Category>?) {
        if (data == null || data.isEmpty()) {
            Toast.makeText(context, "Нет доступных категорий", Toast.LENGTH_SHORT).show()
            return
        }

        allCategoryAdapter?.submitList(data)
        allCategoryAdapter?.notifyDataSetChanged()
    }

    fun newInstance(categoryIdList: List<Int>): AllCategoriesFragment {
        val args = Bundle()
        args.putIntArray(ARG_KEY_CATEGORY_ID_LIST, categoryIdList.toIntArray())

        val fragment = AllCategoriesFragment()
        fragment.arguments = args
        return fragment
    }
}