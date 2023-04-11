package ru.andrewkir.servo.flows.main.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import ru.andrewkir.servo.App
import ru.andrewkir.servo.common.BaseFragment
import ru.andrewkir.data.common.UserPrefsManager
import ru.andrewkir.servo.common.ViewModelFactory
import ru.andrewkir.servo.databinding.FragmentProfileBinding
import ru.andrewkir.servo.flows.auth.login.LoginViewModel
import ru.andrewkir.servo.flows.main.dashboard.DashboardRepository
import ru.andrewkir.servo.flows.main.dashboard.DashboardViewModel
import javax.inject.Inject

class ProfileFragment :
    BaseFragment<ProfileViewModel, ProfileRepository, FragmentProfileBinding>() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

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

        val userPrefsManager = UserPrefsManager(requireContext())
        bind.username.text = userPrefsManager.username
        bind.email.text = userPrefsManager.email

        setupLogout()
    }

    private fun setupLogout() {
        bind.logoutButton.setOnClickListener {
            AlertDialog.Builder(requireContext())
                .setTitle("Выход из приложения")
                .setMessage("Вы действительно хотите выйти из приложения?")
                .setNeutralButton("Отмена") { dialog, _ ->
                    dialog.dismiss()
                }
                .setPositiveButton("Да") { _, _ ->
                    userLogout()
                }
                .show()
        }
    }
}