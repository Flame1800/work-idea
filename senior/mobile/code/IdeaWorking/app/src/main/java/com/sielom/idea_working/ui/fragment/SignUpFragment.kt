package com.sielom.idea_working.ui.fragment

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.sielom.idea_working.R
import com.sielom.idea_working.model.Status
import com.sielom.idea_working.model.User
import com.sielom.idea_working.viewmodel.SignUpFragmentViewModel

class SignUpFragment : Fragment() {
    private var callbacks: Callbacks? = null

    private val signUpFragmentViewModel: SignUpFragmentViewModel by lazy {
        ViewModelProvider(this).get(SignUpFragmentViewModel::class.java)
    }

    private lateinit var signUpButton: Button
    private lateinit var signInTextView: TextView
    private lateinit var nameEditText: EditText
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText

    interface Callbacks {
        fun onSignInTextViewClick()
        fun onSuccessfulSignUp(id: Int)
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
        val view = inflater.inflate(R.layout.fragment_sign_up, container, false)

        signUpButton = view.findViewById(R.id.signUpButton)
        signInTextView = view.findViewById(R.id.signInTextView)
        nameEditText = view.findViewById(R.id.nameEditText)
        emailEditText = view.findViewById(R.id.emailEditText)
        passwordEditText = view.findViewById(R.id.passwordEditText)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        signInTextView.setOnClickListener {
            callbacks?.onSignInTextViewClick()
        }

        signUpButton.setOnClickListener {
            signUpFragmentViewModel.requestSignUp()
        }

        nameEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                signUpFragmentViewModel.username = s?.let { s.toString() } ?: ""
            }

            override fun afterTextChanged(s: Editable?) {
            }

        })

        emailEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                signUpFragmentViewModel.email = s?.let { s.toString() } ?: ""
            }

            override fun afterTextChanged(s: Editable?) {
            }

        })

        passwordEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                signUpFragmentViewModel.password = s?.let { s.toString() } ?: ""
            }

            override fun afterTextChanged(s: Editable?) {
            }

        })

        signUpFragmentViewModel.userLiveData.observe(viewLifecycleOwner) { event ->
            when (event.status) {
                Status.LOADING -> loadingSignUp()
                Status.ERROR -> errorSignUp(event.error)
                Status.SUCCESS -> successSignUp(event.data)
            }
        }
    }

    private fun loadingSignUp() {

    }

    private fun errorSignUp(error: String?) {
        error?.let {
            Toast.makeText(context, error, Toast.LENGTH_LONG).show()
        }
    }

    private fun successSignUp(user: User?) {
        user?.let {
            callbacks?.onSuccessfulSignUp(user.id)
        }

    }

    override fun onDetach() {
        super.onDetach()
        callbacks = null
    }
}