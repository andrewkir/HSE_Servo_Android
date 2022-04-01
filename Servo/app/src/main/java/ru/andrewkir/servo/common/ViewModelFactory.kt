package ru.andrewkir.servo.common

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.andrewkir.servo.flows.auth.login.LoginViewModel
import ru.andrewkir.servo.flows.auth.AuthRepository
import ru.andrewkir.servo.flows.main.dashboard.DashboardRepository
import ru.andrewkir.servo.flows.main.dashboard.DashboardViewModel
import ru.andrewkir.servo.flows.main.profile.ProfileRepository
import ru.andrewkir.servo.flows.main.profile.ProfileViewModel

class ViewModelFactory(
    private val repository: BaseRepository
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> LoginViewModel(repository as AuthRepository) as T
            modelClass.isAssignableFrom(ProfileViewModel::class.java) -> ProfileViewModel(repository as ProfileRepository) as T
            modelClass.isAssignableFrom(DashboardViewModel::class.java) -> DashboardViewModel(repository as DashboardRepository) as T
            else -> throw IllegalArgumentException("Provide correct viewModel class")
        }
    }
}