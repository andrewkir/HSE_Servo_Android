package ru.andrewkir.servo.flows.auth.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import ru.andrewkir.servo.App
import ru.andrewkir.servo.R
import ru.andrewkir.servo.common.BaseFragment
import ru.andrewkir.servo.databinding.FragmentLoginBinding
import ru.andrewkir.servo.databinding.FragmentRegisterBinding
import ru.andrewkir.servo.flows.aspects.finance.FinanceViewModel
import ru.andrewkir.servo.flows.auth.AuthRepository
import ru.andrewkir.servo.network.ApolloProvider

class RegisterFragment : BaseFragment<RegisterViewModel, AuthRepository, FragmentRegisterBinding>() {

    override fun provideViewModel(): RegisterViewModel {
        (requireContext().applicationContext as App).appComponent.inject(this)
        return ViewModelProvider(this, viewModelFactory)[RegisterViewModel::class.java]
    }

    override fun provideBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentRegisterBinding = FragmentRegisterBinding.inflate(inflater, container, false)


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("EDIT_TEXT_LOGIN", bind.loginTextInput.editText!!.text.toString())
        outState.putString("EDIT_TEXT_PASSWORD", bind.passwordTextInput.editText!!.text.toString())
    }

    private fun restoreSavedState(savedInstanceState: Bundle?) {
        savedInstanceState?.let {
            bind.loginTextInput.editText!!.setText(
                savedInstanceState.getString(
                    "EDIT_TEXT_LOGIN",
                    ""
                )
            )

            bind.passwordTextInput.editText!!.setText(
                savedInstanceState.getString(
                    "EDIT_TEXT_PASSWORD",
                    ""
                )
            )
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        restoreSavedState(savedInstanceState)

        setupButtons()
        adjustButtonToText()
        setupInputs()
        subscribeToLoginResult()
        subscribeToLoading()
        subscribeToError()

        bind.progressBar.visibility = View.INVISIBLE
    }

    private fun adjustButtonToText() {
        bind.apply {
            registerButton.isEnabled = loginTextInput.editText!!.text.isNotBlank() &&
                    passwordTextInput.editText!!.text.isNotBlank()
        }
    }

    private fun setupInputs() {
        bind.apply {
            loginTextInput.editText!!.addTextChangedListener { adjustButtonToText() }
            passwordTextInput.editText!!.addTextChangedListener { adjustButtonToText() }
        }
    }

    private fun setupButtons() {
        bind.registerButton.setOnClickListener {
            val login = bind.loginTextInput.editText?.text.toString()
            val password = bind.passwordTextInput.editText?.text.toString()

            viewModel.register(login, password)
        }

        bind.loginTextView.setOnClickListener {
            Navigation.findNavController(bind.root).navigate(R.id.action_registerFragment_to_loginFragment)
        }
    }

    private fun subscribeToLoginResult() {
        viewModel.loginResponse.observe(viewLifecycleOwner) {

        }
    }

    private fun subscribeToError() {
        viewModel.errorResponse.observe(viewLifecycleOwner) {
            Toast.makeText(
                requireContext(),
                it.body,
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun subscribeToLoading() {
        viewModel.loading.observe(viewLifecycleOwner, Observer {
            enableInputsAndButtons(!it)
            bind.progressBar.visibility = if (it) View.VISIBLE else View.INVISIBLE
        })
    }

    private fun enableInputsAndButtons(isEnabled: Boolean) {
        bind.registerButton.isEnabled = isEnabled
        bind.loginTextInput.isEnabled = isEnabled
        bind.passwordTextInput.isEnabled = isEnabled
        bind.loginTextView.isEnabled = isEnabled
    }
}