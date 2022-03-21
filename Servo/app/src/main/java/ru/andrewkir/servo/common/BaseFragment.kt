package ru.andrewkir.servo.common

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding


abstract class BaseFragment<viewModel : BaseViewModel, repo : BaseRepository, viewBinding : ViewBinding> :
    Fragment() {

    protected lateinit var bind: viewBinding
    protected lateinit var viewModel: viewModel

    protected lateinit var userPrefsManager: UserPrefsManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bind = provideBinding(inflater, container)
        userPrefsManager = UserPrefsManager(requireContext())

        val viewModelFactory = ViewModelFactory(provideRepository())
        viewModel = ViewModelProvider(this, viewModelFactory)[provideViewModelClass()]

        return bind.root
    }

//    fun userLogout() = lifecycleScope.launch {
//        val refreshToken = userPrefsManager.refreshToken
//        val api = apiProvider.provideApi(AuthApi::class.java, requireContext(), null, refreshToken)
//
//        viewModel.logoutUser(api)
//
//        userPrefsManager.clearUser()
//        requireActivity().startActivityClearBackStack(AuthActivity::class.java)
//    }


    abstract fun provideViewModelClass(): Class<viewModel>

    abstract fun provideRepository(): repo

    abstract fun provideBinding(inflater: LayoutInflater, container: ViewGroup?): viewBinding
}