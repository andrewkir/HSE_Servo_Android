package ru.andrewkir.servo.di

import androidx.viewbinding.ViewBinding
import dagger.Component
import ru.andrewkir.servo.common.BaseFragment
import ru.andrewkir.servo.common.BaseRepository
import ru.andrewkir.servo.common.BaseViewModel
import ru.andrewkir.servo.flows.aspects.emotions.EmotionsFragment
import ru.andrewkir.servo.flows.aspects.finance.FinanceFragment
import ru.andrewkir.servo.flows.aspects.steps.StepsFragment
import ru.andrewkir.servo.flows.auth.AuthFragment
import ru.andrewkir.servo.flows.auth.login.LoginFragment
import ru.andrewkir.servo.flows.auth.register.RegisterFragment
import ru.andrewkir.servo.flows.main.dashboard.DashboardFragment
import ru.andrewkir.servo.flows.main.profile.ProfileFragment
import javax.inject.Singleton

@Component(modules = [AppModule::class, DataModule::class, ViewModelModule::class])
@Singleton
interface AppComponent {
    fun inject(baseFragment: BaseFragment<BaseViewModel, BaseRepository, ViewBinding>)
    fun inject(dashboardFragment: DashboardFragment)
    fun inject(financeFragment: FinanceFragment)
    fun inject(profileFragment: ProfileFragment)
    fun inject(loginFragment: LoginFragment)
    fun inject(registerFragment: RegisterFragment)
    fun inject(stepsFragment: StepsFragment)
    fun inject(emotionsFragment: EmotionsFragment)
}