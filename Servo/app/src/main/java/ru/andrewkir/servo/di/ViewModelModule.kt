package ru.andrewkir.servo.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import ru.andrewkir.servo.common.ViewModelFactory
import ru.andrewkir.servo.flows.aspects.emotions.EmotionsViewModel
import ru.andrewkir.servo.flows.aspects.finance.FinanceViewModel
import ru.andrewkir.servo.flows.aspects.steps.StepsViewModel
import ru.andrewkir.servo.flows.auth.login.LoginViewModel
import ru.andrewkir.servo.flows.auth.register.RegisterViewModel
import ru.andrewkir.servo.flows.main.dashboard.DashboardViewModel
import ru.andrewkir.servo.flows.main.profile.ProfileViewModel
import ru.andrewkir.tinkofftestcase.di.ViewModelKey

@Module
abstract class ViewModelModule {
    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(ProfileViewModel::class)
    abstract fun profileViewModel(viewModel: ProfileViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(DashboardViewModel::class)
    abstract fun dashboardViewModel(viewModel: DashboardViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(FinanceViewModel::class)
    abstract fun financeViewModel(viewModel: FinanceViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(StepsViewModel::class)
    abstract fun stepsViewModel(viewModel: StepsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(EmotionsViewModel::class)
    abstract fun emotionsViewModel(viewModel: EmotionsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel::class)
    abstract fun loginViewModel(viewModel: LoginViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(RegisterViewModel::class)
    abstract fun registerViewModel(viewModel: RegisterViewModel): ViewModel
}