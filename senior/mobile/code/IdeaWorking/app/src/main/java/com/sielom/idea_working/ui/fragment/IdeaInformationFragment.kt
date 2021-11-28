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
import com.sielom.idea_working.viewmodel.IdeaInformationFragmentViewModel

class IdeaInformationFragment : Fragment() {
    private val ideaInformationFragmentViewModel: IdeaInformationFragmentViewModel by lazy {
        ViewModelProvider(this).get(IdeaInformationFragmentViewModel::class.java)
    }

    private var callbacks: Callbacks? = null

    private var ideaAdapter: IdeaAdapter? = null

    private lateinit var ideaRecyclerView: RecyclerView
    private lateinit var goToInformationAboutPersonsButton: Button


    interface Callbacks {
        fun onGoToInformationAboutPersonsButtonClick()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callbacks = context as Callbacks
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ideaAdapter = IdeaAdapter(LayoutInflater.from(context))

        ideaInformationFragmentViewModel.requestGetIdeas()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.idea_information_fragment, container, false)

        ideaRecyclerView = view.findViewById(R.id.ideaRecyclerView)
        goToInformationAboutPersonsButton =
            view.findViewById(R.id.goToInformationAboutPersonsButton)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ideaRecyclerView.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = ideaAdapter
        }

        goToInformationAboutPersonsButton.setOnClickListener {
            callbacks?.onGoToInformationAboutPersonsButtonClick()
        }

        ideaInformationFragmentViewModel.ideasLiveData.observe(viewLifecycleOwner) { event ->
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
            Toast.makeText(context, "Не удалось загрузить идеи", Toast.LENGTH_SHORT).show()
            return
        }

        ideaAdapter?.submitList(data)
        ideaAdapter?.notifyDataSetChanged()
    }

    override fun onDetach() {
        super.onDetach()
        callbacks = null
    }
}