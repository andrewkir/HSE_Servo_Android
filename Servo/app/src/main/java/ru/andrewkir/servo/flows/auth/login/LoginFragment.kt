package ru.andrewkir.servo.flows.auth.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import ru.andrewkir.servo.App
import ru.andrewkir.servo.common.BaseFragment
import ru.andrewkir.servo.databinding.FragmentLoginBinding
import ru.andrewkir.servo.flows.aspects.finance.FinanceViewModel
import ru.andrewkir.servo.flows.auth.AuthRepository
import ru.andrewkir.servo.network.ApolloProvider

class LoginFragment : BaseFragment<LoginViewModel, AuthRepository, FragmentLoginBinding>() {

    override fun provideViewModel(): LoginViewModel {
        (requireContext().applicationContext as App).appComponent.inject(this)
        return ViewModelProvider(this, viewModelFactory)[LoginViewModel::class.java]
    }

    override fun provideBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentLoginBinding = FragmentLoginBinding.inflate(inflater, container, false)


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

        bind.progressBar.visibility = View.INVISIBLE
    }

    private fun adjustButtonToText() {
        bind.apply {
            loginButton.isEnabled = loginTextInput.editText!!.text.isNotBlank() &&
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
        bind.loginButton.setOnClickListener {
            val login = bind.loginTextInput.editText?.text.toString()
            val password = bind.passwordTextInput.editText?.text.toString()

            if (android.util.Patterns.EMAIL_ADDRESS.matcher(login).matches()) {
                viewModel.loginByUsername(login, password)
            } else {
                viewModel.loginByUsername(login, password)
            }
        }

//        bind.registerTextView.setOnClickListener {
//            Navigation.findNavController(bind.root).navigate(R.id.login_to_register)
//        }
    }

    private fun subscribeToLoginResult() {
        viewModel.loginResponse.observe(viewLifecycleOwner, Observer {
            Toast.makeText(
                requireContext(),
                it.signupUser.firstName,
                Toast.LENGTH_SHORT
            ).show()

//                is ApiResponse.OnSuccessResponse -> {
//                    userPrefsManager.accessToken = it.value.access_token
//                    userPrefsManager.refreshToken = it.value.refresh_token
//
//                    userPrefsManager.username = it.value.user.username
//                    userPrefsManager.email = it.value.user.email
            //          requireActivity().startActivityClearBackStack(MainScreenActivity::class.java)
//                }
//                is ApiResponse.OnErrorResponse -> handleApiError(it) {
//                    if (bind.loginButton.isEnabled) bind.loginButton.performClick()
//                }
        })
    }

    private fun subscribeToLoading() {
        viewModel.loading.observe(viewLifecycleOwner, Observer {
            enableInputsAndButtons(!it)
            bind.progressBar.visibility = if (it) View.VISIBLE else View.INVISIBLE
        })
    }

    private fun enableInputsAndButtons(isEnabled: Boolean) {
        bind.loginButton.isEnabled = isEnabled
        bind.loginTextInput.isEnabled = isEnabled
        bind.passwordTextInput.isEnabled = isEnabled
        bind.registerTextView.isEnabled = isEnabled
    }
}