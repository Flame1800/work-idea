package com.sielom.idea_working.ui.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.sielom.idea_working.R

class EndSliderFragment : Fragment() {

    private var callbacks: Callbacks? = null

    private lateinit var okButton: Button

    interface Callbacks {
        fun onOkButtonClick()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callbacks = context as Callbacks
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_end_slider, container, false)

        okButton = view.findViewById(R.id.okButton)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        okButton.setOnClickListener {
            callbacks?.onOkButtonClick()
        }
    }

    override fun onDetach() {
        super.onDetach()
        callbacks = null
    }
}