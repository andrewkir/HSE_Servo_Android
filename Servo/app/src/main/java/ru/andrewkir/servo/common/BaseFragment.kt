package ru.andrewkir.servo.common

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding
import kotlinx.coroutines.launch
import ru.andrewkir.servo.App
import ru.andrewkir.servo.flows.auth.AuthActivity
import javax.inject.Inject


abstract class BaseFragment<viewModel : BaseViewModel, repo : BaseRepository, viewBinding : ViewBinding> :
    Fragment() {

    protected lateinit var bind: viewBinding
    protected lateinit var viewModel: viewModel

    protected lateinit var userPrefsManager: UserPrefsManager

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bind = provideBinding(inflater, container)
        userPrefsManager = UserPrefsManager(requireContext())
        viewModel = provideViewModel()

        return bind.root
    }

    fun userLogout() = lifecycleScope.launch {
        userPrefsManager.clearUser()
        requireActivity().startActivityClearBackStack(AuthActivity::class.java)
    }


    abstract fun provideViewModel(): viewModel

    abstract fun provideBinding(inflater: LayoutInflater, container: ViewGroup?): viewBinding
}