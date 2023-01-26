package ru.andrewkir.servo.flows.main.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import ru.andrewkir.servo.App
import ru.andrewkir.servo.common.BaseFragment
import ru.andrewkir.servo.databinding.EmptyFragmentBinding
import ru.andrewkir.servo.databinding.FragmentProfileBinding
import ru.andrewkir.servo.flows.auth.login.LoginViewModel
import ru.andrewkir.servo.flows.main.dashboard.DashboardRepository
import ru.andrewkir.servo.flows.main.dashboard.DashboardViewModel

class ProfileFragment :
    BaseFragment<ProfileViewModel, ProfileRepository, FragmentProfileBinding>() {

    override fun provideViewModel(): ProfileViewModel {
        (requireContext().applicationContext as App).appComponent.inject(this)
        return ViewModelProvider(this, viewModelFactory)[ProfileViewModel::class.java]
    }

    override fun provideBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentProfileBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bind.logoutButton.setOnClickListener {
            userLogout()
        }
    }
}