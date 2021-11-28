package com.sielom.idea_working.ui.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.sielom.idea_working.R

class GuestFragment : Fragment() {
    private var callbacks: Callbacks? = null

    private lateinit var signUpButton: Button
    private lateinit var signInButton: Button
    private lateinit var startSliderButton: Button

    interface Callbacks {
        fun onSignUpButtonClick()
        fun onSignInButtonClick()
        fun onStartSliderButtonClick()
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
        val view = inflater.inflate(R.layout.guest_fragment, container, false)

        signUpButton = view.findViewById(R.id.signUpButton)
        signInButton = view.findViewById(R.id.signInButton)
        startSliderButton = view.findViewById(R.id.startSliderButton)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        signUpButton.setOnClickListener {
            callbacks?.onSignUpButtonClick()
        }

        signInButton.setOnClickListener {
            callbacks?.onSignInButtonClick()
        }

        startSliderButton.setOnClickListener {
            callbacks?.onStartSliderButtonClick()
        }
    }

    override fun onDetach() {
        super.onDetach()
        callbacks = null
    }
}