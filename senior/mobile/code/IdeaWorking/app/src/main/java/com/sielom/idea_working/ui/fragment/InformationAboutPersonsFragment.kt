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
import com.sielom.idea_working.adapter.SpecialistAdapter
import com.sielom.idea_working.model.Status
import com.sielom.idea_working.model.User
import com.sielom.idea_working.viewmodel.InformationAboutPersonsFragmentViewModel

class InformationAboutPersonsFragment : Fragment() {
    private val informationAboutPersonsFragmentViewModel: InformationAboutPersonsFragmentViewModel by lazy {
        ViewModelProvider(this).get(InformationAboutPersonsFragmentViewModel::class.java)
    }

    private var callbacks: Callbacks? = null

    private var specialistAdapter: SpecialistAdapter? = null

    private lateinit var personRecyclerView: RecyclerView
    private lateinit var goToExampleWorkButton: Button

    interface Callbacks {
        fun onGoToExampleWorkButtonClick()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callbacks = context as Callbacks
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        specialistAdapter = SpecialistAdapter(LayoutInflater.from(context))

        informationAboutPersonsFragmentViewModel.requestGetUsers()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_information_about_persons, container, false)

        personRecyclerView = view.findViewById(R.id.personRecyclerView)
        goToExampleWorkButton = view.findViewById(R.id.goToExampleWorkButton)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        personRecyclerView.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = specialistAdapter
        }

        goToExampleWorkButton.setOnClickListener {
            callbacks?.onGoToExampleWorkButtonClick()
        }

        informationAboutPersonsFragmentViewModel.specialistsLiveData.observe(viewLifecycleOwner) { event ->
            when (event.status) {
                Status.LOADING -> loadingGetUsers()
                Status.ERROR -> errorGetUsers(event.error)
                Status.SUCCESS -> successGetUsers(event.data)
            }
        }
    }

    private fun loadingGetUsers() {

    }

    private fun errorGetUsers(error: String?) {
        Toast.makeText(context, error, Toast.LENGTH_LONG).show()
    }

    private fun successGetUsers(data: List<User?>?) {
        if (data == null || data.isEmpty()) {
            Toast.makeText(context, "Не удалось получить пользователей", Toast.LENGTH_LONG).show()
            return
        }

        specialistAdapter?.submitList(data)
    }

    override fun onDetach() {
        super.onDetach()
        callbacks = null
    }
}