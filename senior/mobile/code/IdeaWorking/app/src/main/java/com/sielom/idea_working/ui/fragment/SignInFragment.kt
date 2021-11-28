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
import com.sielom.idea_working.viewmodel.SignInFragmentViewModel

class SignInFragment : Fragment() {
    private val signInFragmentViewModel: SignInFragmentViewModel by lazy {
        ViewModelProvider(this).get(SignInFragmentViewModel::class.java)
    }

    private var callbacks: Callbacks? = null

    private lateinit var createAccountTextView: TextView
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var signInButton: Button

    interface Callbacks {
        fun onCreateAccountTextView()
        fun onSuccessfulSignIn(userId: Int)
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
        val view = inflater.inflate(R.layout.fragment_sign_in, container, false)

        createAccountTextView = view.findViewById(R.id.createAccountTextView)
        emailEditText = view.findViewById(R.id.emailEditText)
        passwordEditText = view.findViewById(R.id.passwordEditText)
        signInButton = view.findViewById(R.id.signInButton)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        createAccountTextView.setOnClickListener {
            callbacks?.onCreateAccountTextView()
        }

        signInButton.setOnClickListener {
            signInFragmentViewModel.requestSignIn()
        }

        emailEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                s?.let {
                    signInFragmentViewModel.email = s.toString()
                }
            }

            override fun afterTextChanged(s: Editable?) {
            }

        })

        passwordEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                s?.let {
                    signInFragmentViewModel.password = s.toString()
                }
            }

            override fun afterTextChanged(s: Editable?) {
            }

        })

        signInFragmentViewModel.userLiveData.observe(viewLifecycleOwner) { event ->
            when (event.status) {
                Status.LOADING -> loadingSignIn()
                Status.ERROR -> errorSignIn(event.error)
                Status.SUCCESS -> successSignIn(event.data)
            }
        }
    }

    private fun loadingSignIn() {

    }

    private fun errorSignIn(error: String?) {
        Toast.makeText(context, error, Toast.LENGTH_LONG).show()
    }

    private fun successSignIn(data: User?) {
        if (data == null) {
            Toast.makeText(context, "Не удалось авторизироваться", Toast.LENGTH_LONG).show()
            return
        }

        callbacks?.onSuccessfulSignIn(data.id)
    }

    override fun onDetach() {
        super.onDetach()
        callbacks = null
    }
}