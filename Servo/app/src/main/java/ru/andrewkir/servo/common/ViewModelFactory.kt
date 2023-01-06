package ru.andrewkir.servo.common

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.andrewkir.servo.flows.aspects.emotions.EmotionsRepository
import ru.andrewkir.servo.flows.aspects.emotions.EmotionsViewModel
import ru.andrewkir.servo.flows.aspects.finance.FinanceRepository
import ru.andrewkir.servo.flows.aspects.finance.FinanceViewModel
import ru.andrewkir.servo.flows.aspects.steps.StepsRepository
import ru.andrewkir.servo.flows.aspects.steps.StepsViewModel
import ru.andrewkir.servo.flows.auth.login.LoginViewModel
import ru.andrewkir.servo.flows.auth.AuthRepository
import ru.andrewkir.servo.flows.auth.register.RegisterViewModel
import ru.andrewkir.servo.flows.main.dashboard.DashboardRepository
import ru.andrewkir.servo.flows.main.dashboard.DashboardViewModel
import ru.andrewkir.servo.flows.main.profile.ProfileRepository
import ru.andrewkir.servo.flows.main.profile.ProfileViewModel
import javax.inject.Inject

class ViewModelFactory @Inject constructor(
    val authRepository: AuthRepository,
    val dashboardRepository: DashboardRepository,
    val profileRepository: ProfileRepository,
    val financeRepository: FinanceRepository,
    val stepsRepository: StepsRepository,
    val emotionsRepository: EmotionsRepository
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> LoginViewModel(authRepository) as T
            modelClass.isAssignableFrom(ProfileViewModel::class.java) -> ProfileViewModel(profileRepository) as T
            modelClass.isAssignableFrom(DashboardViewModel::class.java) -> DashboardViewModel(dashboardRepository, financeRepository, stepsRepository, emotionsRepository) as T
            modelClass.isAssignableFrom(FinanceViewModel::class.java) -> FinanceViewModel(financeRepository) as T
            modelClass.isAssignableFrom(StepsViewModel::class.java) -> StepsViewModel(stepsRepository) as T
            modelClass.isAssignableFrom(EmotionsViewModel::class.java) -> EmotionsViewModel(emotionsRepository) as T
            modelClass.isAssignableFrom(RegisterViewModel::class.java) -> RegisterViewModel(authRepository) as T
            else -> throw IllegalArgumentException("Provide correct viewModel class")
        }
    }
}