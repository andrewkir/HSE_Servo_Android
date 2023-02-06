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
import javax.inject.Provider
import javax.inject.Singleton

@Singleton
class ViewModelFactory @Inject constructor(private val viewModels: MutableMap<Class<out ViewModel>,
        @JvmSuppressWildcards Provider<ViewModel>>): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        viewModels[modelClass]?.get() as T
}