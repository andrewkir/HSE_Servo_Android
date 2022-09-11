package ru.andrewkir.servo.di

import android.content.Context
import dagger.Module
import dagger.Provides
import ru.andrewkir.servo.common.ViewModelFactory
import ru.andrewkir.servo.flows.aspects.finance.FinanceRepository
import ru.andrewkir.servo.flows.aspects.steps.StepsRepository
import ru.andrewkir.servo.flows.auth.AuthRepository
import ru.andrewkir.servo.flows.main.dashboard.DashboardRepository
import ru.andrewkir.servo.flows.main.profile.ProfileRepository

@Module
class AppModule(val context: Context) {
    @Provides
    fun provideContext(): Context = context

    @Provides
    fun provideViewModelFactory(
        authRepository: AuthRepository,
        dashboardRepository: DashboardRepository,
        profileRepository: ProfileRepository,
        financeRepository: FinanceRepository,
        stepsRepository: StepsRepository
    ): ViewModelFactory {
        return ViewModelFactory(
            authRepository,
            dashboardRepository,
            profileRepository,
            financeRepository,
            stepsRepository
        )
    }
}